var fs = require('fs')
var http = require('http')
var socketio = require('socket.io')
// 建立server 放在4040
var options = {
    //https 的一些設定，如果不需要使用 ssl 加密連線的話，把內容註解掉就好
}
var server = http.createServer(options)
server.listen(4040)
var io = socketio(server);
console.log("Server socket 4040 , api 4000")

// api port,建立api的連線
// 處理使用者發送出來的請求 via port4000
var app = require('express')();
var port = 4000;
app.listen(port, function () {
    console.log('API listening on *:' + port);
});

//用api方式取得
app.get('/api/messages', function (req, res) {
    let messages = 'hellow world'
    res.send(messages);
})

// var userCursorList = [];
var userCursorMap = new Map();
io.on('connection', function (socket) {
    //監聽連上來了(connection)的事件 使用socket來處理
    // console.log("A user connected.", userCursorList);
    io.emit("userCursorUpdate", JSON.stringify(Array.from(userCursorMap)))
    socket.on('mouse-moved', function (message) {
        userCursorMap.set(message.id,message.position);
        let transitString = JSON.stringify(Array.from(userCursorMap));
        io.emit("userCursorUpdate", transitString)
    })
})
