const canva = document.getElementById("myCanvas");


let ctx = canva.getContext("2d");




window.onload = function () {
    ctx.beginPath();
    ctx.fillStyle="#f9f900";
    ctx.fillRect(0, 20, 200, 100);
    ctx.stroke();

};



let ax, ay, x, y;

//添加鼠标按下事件
canva.onmousedown = function (e) {
    //按下后可移动
    canva.onmousemove = function (e) {
        x = e.clientX;
        y = e.clientY;

        //限制移动不能超出画布
        x < 173 ? (ax = 75) : (ax = 425);
        y < 148 ? (ay = 50) : (ay = 350);

        x < 425 && x > 75 ? (x = e.clientX) : (x = ax);

        y > 50 && y < 350 ? (y = e.clientY) : (y = ay);

        //先清除之前的然后重新绘制
        // ctx.clearRect(0, 0, canva.width, canva.height);

        // ctx.drawImage(img, x - 75, y - 50, 150, 100);
    };

    //鼠标抬起清除绑定事件
    canva.onmouseup = function () {
        canva.onmousemove = null;
        canva.onmouseup = null;
    };
};
