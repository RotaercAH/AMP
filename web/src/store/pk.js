export default {
  state: {
        status: "playing", //matching表示匹配界面，playing表示对战界面
        socket: null,
        opponent_username: "",
        opponent_photo: "",
  },
  getters: {
  },
  mutations: {
        // updateSocket(state, socket){
        //     state.socket = socket;
        // },
        // updateOpponent(state, opponent){
        //     state.opponent_photo = opponent_photo;
        //     state.opponent_username = opponent_username
        // },
        // updateStatus(state, status){
        //     state.status = status;
        // },
  },
  actions: {
  },
  modules: {
  }
}
