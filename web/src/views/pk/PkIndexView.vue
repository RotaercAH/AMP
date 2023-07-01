<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'"/>
</template>

<script>
import PlayGround from "../../components/PlayGround.vue"
import { onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";


export default {
    components: {
        PlayGround
    },

    setup() {
        const stroe = useStore();
        const socketUrl = `ws://127.0.0.1:8080/websocket/${stroe.state.user.token}/`;


        let socket = null;
        onMounted(() => {
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("connected!");    
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                console.log(data);
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }
        });

        onUnmounted(()=>{
            socket.close();
        });
    }
}
</script>
