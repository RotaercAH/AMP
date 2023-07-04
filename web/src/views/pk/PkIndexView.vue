<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'"/>
    <MatchGround v-if="$store.state.pk.status === 'matching'"/>
</template>

<script>
import PlayGround from "../../components/PlayGround.vue"
import MatchGround from "@/components/MatchGround.vue";
import store from "@/store";
import { onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";


export default {
    components: {
        PlayGround,
        MatchGround
    },

    setup() {
        const stroe = useStore();
        const socketUrl = `ws://127.0.0.1:8080/websocket/${stroe.state.user.token}/`;


        let socket = null;
        onMounted(() => {
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://img0.baidu.com/it/u=3491863486,2489088886&fm=253&fmt=auto&app=138&f=JPEG?w=311&h=500"
            })
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);  
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (data.event === "start-matching") {
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");
                    }, 2000);
                    store.commit("updateGamemap", data.gamemap);
                    
                }
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }
        });

        onUnmounted(()=>{
            socket.close();
            store.commit("updateStatus", "matching");
        });
    }
}
</script>
