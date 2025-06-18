<!DOCTYPE html>
<#import "/spring.ftl" as spring1 />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring1.message code="profile.view.title" /> ${viewedUser.firstName} ${viewedUser.lastName}</title>

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
    <div class="profile d-flex mb-5">
        <div class="avatar me-3">
            <img src="/files/${viewedUser.avatar.storageFileName}"
                 alt="<@spring.message code='friends.avatar'/>"
                 class="rounded-circle"
                 width="128" height="128">
        </div>
        <div class="info">
            <h2>
                ${viewedUser.firstName} ${viewedUser.lastName}
                <#if viewedUser.age??>, ${viewedUser.age} <@spring.message code="profile.view.years"/></#if>
            </h2>
            <p>${viewedUser.bio!''}</p>

            <#if user.id == viewedUser.id>
                <div class="row gx-2 gy-2">
                    <div class="col-auto d-flex flex-column">
                        <a class="btn btn-outline-primary mb-2" href="/profile/edit">
                            <@spring.message code="profile.view.edit"/>
                        </a>
                        <a class="btn btn-outline-success" href="/friends">
                            <@spring.message code="profile.view.friends"/>
                        </a>
                    </div>
                    <div class="col-auto d-flex flex-column">
                        <form method="post" action="/logout" class="mb-2">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>
                            <button type="submit" class="btn btn-outline-danger">
                                <@spring.message code="profile.view.logout"/>
                            </button>
                        </form>
                        <a class="btn btn-outline-warning" href="/friend-requests">
                            <@spring.message code="profile.view.requests"/>
                            <#if friendRequestsCount?? && friendRequestsCount gt 0>
                                (${friendRequestsCount})
                            </#if>
                        </a>
                    </div>
                </div>
            </#if>

            <#if user.id != viewedUser.id>
                <div class="d-flex flex-wrap gap-2">
                    <#if areFriends>
                        <a href="/chat/${viewedUser.id}" class="btn btn-outline-info">
                            <i class="bi bi-chat-dots"></i> <@spring.message code="profile.view.message"/>
                        </a>
                    <#elseif isFriendOrPending>
                        <button class="btn btn-secondary" disabled><@spring.message code="profile.view.requestSent"/></button>
                    <#else>
                        <button id="add-friend-btn" class="btn btn-primary"><@spring.message code="profile.view.addFriend"/></button>
                    </#if>
                </div>
            </#if>
        </div>
    </div>

    <h2 class="mb-3"><@spring.message code="profile.view.myPosts"/></h2>

    <#if postsPage.content?size == 0>
        <p class="text-muted"><@spring.message code="profile.view.noPosts"/></p>
    <#else>
        <#list postsPage.content as post>
            <div class="card mb-4">
                <div class="card-body">
                    <#if post.title?? && post.title?has_content>
                        <h5 class="card-title">${post.title}</h5>
                    </#if>
                    <p class="card-text">${post.content}</p>
                    <#if post.location?? && post.location?has_content>
                        <p class="card-text mb-1"><span class="badge bg-secondary">${post.location}</span></p>
                    </#if>
                    <#if post.image??>
                        <div class="ratio ratio-4x3 mb-2" style="max-width: 300px;">
                            <img
                                    src="/files/${post.image.storageFileName}"
                                    alt="<@spring.message code='post.create.imageLabel'/>"
                                    class="img-fluid object-fit-cover"
                            >
                        </div>
                    </#if>

                    <p class="card-text mb-2">
                        <small class="text-muted">${dateConverter.convert(post.createdAt)}</small>
                    </p>

                    <button class="btn btn-like p-0 border-0 bg-transparent" data-post-id="${post.id}" id="like-btn-${post.id}">
                        <i class="bi <#if post.likedBy?map(u -> u.id)?seq_contains(user.id)>bi-heart-fill text-danger<#else>bi-heart text-secondary</#if> fs-4"></i>
                    </button>
                    <span id="like-count-${post.id}" class="ms-1">${post.likedBy?size}</span>

                    <hr>
                    <div class="comments" id="comments-${post.id}"></div>
                    <div class="comment-controls mt-2">
                        <button class="btn btn-link btn-sm show-all-comments" id="show-btn-${post.id}" data-post-id="${post.id}">
                            <@spring.message code="profile.view.showAllComments"/>
                        </button>
                        <button class="btn btn-link btn-sm hide-comments d-none" id="hide-btn-${post.id}" data-post-id="${post.id}">
                            <@spring.message code="profile.view.hideComments"/>
                        </button>
                    </div>

                    <form class="comment-form mt-2" data-post-id="${post.id}">
                        <div class="input-group">
                            <input type="text" class="form-control" name="content" placeholder="<@spring.message code='profile.view.commentPlaceholder'/>" required maxlength="3000">
                            <button class="btn btn-outline-secondary" type="submit"><@spring.message code="profile.view.send"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </#list>

        <#if postsPage.totalPages gt 1>
            <nav aria-label="Навигация по страницам">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if postsPage.number == 0>disabled</#if>">
                        <a class="page-link" href="?page=${postsPage.number - 1}&size=${postsPage.size}">&laquo;</a>
                    </li>
                    <#list 0..(postsPage.totalPages - 1) as i>
                        <li class="page-item <#if i == postsPage.number>active</#if>">
                            <a class="page-link" href="?page=${i}&size=${postsPage.size}">${i + 1}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if postsPage.number == postsPage.totalPages - 1>disabled</#if>">
                        <a class="page-link" href="?page=${postsPage.number + 1}&size=${postsPage.size}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </#if>
    </#if>
</div>

<#include "fragments/footer.ftlh">

<template id="comment-template">
    <div class="mb-2 d-flex justify-content-between align-items-start comment" id="">
        <div>
            <a href="#" class="comment-author fw-semibold text-decoration-none me-1"></a>
            <span class="comment-text"></span><br>
            <small class="comment-date text-muted"></small>
        </div>
        <div class="d-flex align-items-center">
            <button class="btn btn-sm btn-comment-like me-2" data-comment-id="">
                <i class="bi bi-heart text-secondary"></i>
                <span class="comment-like-count">0</span>
            </button>
            <button class="btn btn-sm btn-delete-comment text-danger d-none" data-comment-id=""><@spring1.message code="profile.view.delete"/></button>
        </div>
    </div>
</template>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
    $(function () {
        const token = $('meta[name="_csrf"]').attr('content');
        const header = $('meta[name="_csrf_header"]').attr('content');
        $.ajaxSetup({
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        function renderComment(comment, container, isExtra = false) {
            const template = document.getElementById('comment-template');
            const clone = template.content.cloneNode(true);
            const commentElement = $(clone).children().first();

            commentElement.attr('id', 'comment-' + comment.id);
            commentElement.find('.comment-author').text(comment.authorName).attr('href', '/profile/' + comment.authorId);
            commentElement.find('.comment-text').text(comment.text);
            commentElement.find('.comment-date').text(comment.createdAt);

            const icon = commentElement.find('i');
            icon.toggleClass('bi-heart-fill text-danger', comment.likedByCurrentUser);
            icon.toggleClass('bi-heart text-secondary', !comment.likedByCurrentUser);

            commentElement.find('.comment-like-count').text(comment.likeCount);
            commentElement.find('.btn-comment-like').attr('data-comment-id', comment.id);
            commentElement.find('.btn-delete-comment').attr('data-comment-id', comment.id);

            if (comment.deletable) {
                commentElement.find('.btn-delete-comment').removeClass('d-none');
            }

            if (isExtra) {
                commentElement.addClass('extra');
            }

            container.append(commentElement);
        }

        $('.comments').each(function () {
            const container = $(this);
            const postId = container.attr('id').split('-')[1];
            $.get('/api/posts/' + postId + '/comments?top=true', function (comments) {
                comments.forEach(comment => renderComment(comment, container));
            }).fail(function () {
                alert('Не удалось загрузить комментарии.');
            });
        });

        $('.show-all-comments').click(function () {
            const postId = $(this).data('post-id');
            const container = $('#comments-' + postId);
            const showBtn = $('#show-btn-' + postId);
            const hideBtn = $('#hide-btn-' + postId);

            $.get('/api/posts/' + postId + '/comments', function (comments) {
                container.empty();
                comments.forEach((comment, idx) => renderComment(comment, container, idx >= 2));
                showBtn.addClass('d-none');
                hideBtn.removeClass('d-none');
            }).fail(function () {
                alert('Не удалось загрузить все комментарии.');
            });
        });

        $('.hide-comments').click(function () {
            const postId = $(this).data('post-id');
            const container = $('#comments-' + postId);
            const showBtn = $('#show-btn-' + postId);
            const hideBtn = $('#hide-btn-' + postId);

            $.get('/api/posts/' + postId + '/comments?top=true', function (comments) {
                container.empty();
                comments.forEach(comment => renderComment(comment, container));
                hideBtn.addClass('d-none');
                showBtn.removeClass('d-none');
            }).fail(function () {
                alert('Не удалось обновить комментарии.');
            }).fail(function () {
                alert('Не удалось поставить лайк посту.');
            });
        });

        $('.btn-like').click(function () {
            const postId = $(this).data('post-id');
            $.post('/api/posts/' + postId + '/like', function (data) {
                const icon  = $('#like-btn-' + postId + ' i');
                const count = $('#like-count-' + postId);
                if (data.liked) {
                    icon.removeClass('bi-heart text-secondary').addClass('bi-heart-fill text-danger');
                    count.text(parseInt(count.text()) + 1);
                } else {
                    icon.removeClass('bi-heart-fill text-danger').addClass('bi-heart text-secondary');
                    count.text(parseInt(count.text()) - 1);
                }
            }).fail(function () {
                alert('Не удалось поставить лайк комментарию.');
            });
        });

        $(document).on('click', '.btn-comment-like', function () {
            const button = $(this);
            const commentId = button.data('comment-id');
            const icon = button.find('i');
            const count = button.find('.comment-like-count');
            $.post('/api/comments/' + commentId + '/like', function (data) {
                if (data.liked) {
                    icon.removeClass('bi-heart text-secondary').addClass('bi-heart-fill text-danger');
                    count.text(parseInt(count.text()) + 1);
                } else {
                    icon.removeClass('bi-heart-fill text-danger').addClass('bi-heart text-secondary');
                    count.text(parseInt(count.text()) - 1);
                }
            });
        });

        $(document).on('click', '.btn-delete-comment', function () {
            const commentId = $(this).data('comment-id');
            $.ajax({
                url: '/api/comments/' + commentId,
                type: 'DELETE',
                success: function () {
                    $('#comment-' + commentId).fadeOut(300, function () {
                        $(this).remove();
                    });
                },
                error: function () {
                    alert('Не удалось удалить комментарий.');
                }
            });
        });

        $('.comment-form').submit(function (e) {
            e.preventDefault();
            const form = $(this);
            const postId = form.data('post-id');
            const input = form.find('input[name="content"]');
            const text = input.val().trim();
            if (!text) return;

            $.ajax({
                url: '/api/posts/' + postId + '/comments',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ content: text }),
                success: function (comment) {
                    const container = $('#comments-' + postId);
                    renderComment(comment, container);
                    input.val('');
                }
            });
        });

        $('#add-friend-btn').on('click', function () {
            $.ajax({
                method: 'POST',
                url: '/friendships',
                contentType: 'application/json',
                data: JSON.stringify({ friendId: ${viewedUser.id} }),
                success: function () {
                    $('#add-friend-btn')
                        .text('Запрос отправлен')
                        .removeClass('btn-primary')
                        .addClass('btn-secondary')
                        .prop('disabled', true);
                },
                error: function () {
                    alert('Ошибка при отправке запроса');
                }
            });
        });
    });
</script>
</body>
</html>
