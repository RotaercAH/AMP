const GAME_OBJECTS = [];

export class GameObject {
    constructor(){
        GAME_OBJECTS.push(this);
        this.timedalta = 0; //帧与帧之间的时间间隔
        this.has_called_start = false;// 标记是否被执行
    }

    start() {

    }

    update() {

    }

    destroy() {
        for(let i in GAME_OBJECTS) {
            const obj = GAME_OBJECTS[i];
            if(obj == this) {
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }

}

let last_timestamp; //上一次执行的时刻

const step = timestamp => {
    //遍历所有物体，没有执行过start函数的要执行start，执行过start的执行update
    for (let obj of GAME_OBJECTS) {
        if(!obj.has_called_start){
            obj.has_called_start = true;
            obj.start();
        }else{
            obj.timedalta = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step)