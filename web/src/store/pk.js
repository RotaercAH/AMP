export default {
  state: {
        status: "matching", //matching表示匹配界面，playing表示对战界面
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        gamemap:null,
  },
  getters: {
  },
  mutations: {
        updateSocket(state, socket){
            state.socket = socket;
        },
        updateOpponent(state, opponent){
            state.opponent_photo = opponent.photo;
            state.opponent_username = opponent.username
        },
        updateStatus(state, status){
            state.status = status;
        },
        updateGamemap(state, gamemap){
            state.gamemap = gamemap;
        }
  },
  actions: {
  },
  modules: {
  }
}
