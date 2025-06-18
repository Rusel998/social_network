<!DOCTYPE html>
<#import "/spring.ftl" as spring1 />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring1.message code="chat.title"/> ${friend.firstName} ${friend.lastName}</title>

    <#if _csrf??>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf" content="${_csrf.token}"/>
    </#if>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<#include "fragments/header.ftlh">

<div class="container flex-grow-1 py-4">

    <div id="chat-context"
         data-user-id="${user.id}"
         data-friend-id="${friend.id}"
         data-friend-name="${friend.firstName?js_string}">
    </div>

    <div class="d-flex align-items-center mb-4">
        <img src="/files/${friend.avatar.storageFileName}" alt="<@spring.message code='chat.avatar'/>"
             class="rounded-circle me-3" width="64" height="64">
        <h2 class="mb-0"><@spring.message code="chat.with"/> ${friend.firstName} ${friend.lastName}</h2>
    </div>

    <div class="card mb-4">
        <div class="card-body" id="messages" style="height: 400px; overflow-y: auto;">
            <#list messages as msg>
                <#if msg.senderId == user.id>
                    <div class="mb-2 text-break">
                        <b class="text-primary"><@spring.message code="chat.me"/>:</b> ${msg.content}
                        <br>
                        <small class="text-muted">${dateConverter.convert(msg.sentAt)}</small>
                    </div>
                <#else>
                    <div class="mb-2 text-break">
                        <b class="text-dark">${friend.firstName}:</b> ${msg.content}
                        <br>
                        <small class="text-muted">${dateConverter.convert(msg.sentAt)}</small>
                    </div>
                </#if>
            </#list>
        </div>
        <div class="card-footer">
            <div class="input-group">
                <input type="text" class="form-control" id="msgInput" placeholder="<@spring.message code='chat.input.placeholder'/>" maxlength="5000">
                <button class="btn btn-outline-primary" onclick="sendMessage()"><@spring.message code="chat.send"/></button>
            </div>
        </div>
    </div>
</div>

<#include "fragments/footer.ftlh">

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<script>
    const ctx = document.getElementById("chat-context");
    const userId = Number(ctx.dataset.userId);
    const friendId = Number(ctx.dataset.friendId);
    const friendFirstName = ctx.dataset.friendName;

    let stompClient = null;

    function connect() {
        const socket = new SockJS('/ws-chat');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
            stompClient.subscribe('/user/' + userId + '/topic/messages', function (msg) {
                const message = JSON.parse(msg.body);
                showMessage(message);
            });
        });
    }

    function sendMessage() {
        const input = document.getElementById("msgInput");
        const content = input.value.trim();
        if (!content) return;

        stompClient.send("/app/chat.send", {}, JSON.stringify({
            senderId: userId,
            recipientId: friendId,
            content: content
        }));

        input.value = '';
    }

    function showMessage(msg) {
        const container = document.getElementById("messages");
        const div = document.createElement("div");
        div.classList.add("mb-2", "text-break");

        const isMe = msg.senderId === userId;
        const senderLabel = isMe ? '<@spring.message code="chat.me"/>' : friendFirstName;
        const className = isMe ? "text-primary" : "text-dark";
        const formattedDate = formatDate(msg.sentAt);

        const html = '<b class="' + className + '">' + senderLabel + ':</b> ' +
            msg.content + '<br>' +
            '<small class="text-muted">' + formattedDate + '</small>';

        div.innerHTML = html;
        container.appendChild(div);
        container.scrollTop = container.scrollHeight;
    }

    connect();

    function formatDate(dateStr) {
        const date = new Date(dateStr);
        return date.toLocaleString("${lang}" || "ru-RU", {
            day: "2-digit",
            month: "long",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit"
        });
    }
</script>

</body>
</html>