<!DOCTYPE html>
<#import "/spring.ftl" as spring1 />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring1.message code="friends.title" /></title>
    <#if _csrf??>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf" content="${_csrf.token}"/>
    </#if>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<#include "fragments/header.ftlh">
<div class="container py-4">
    <h2><@spring.message code="friends.title" /></h2>
    <#if friends?size == 0>
        <p class="text-muted"><@spring.message code="friends.empty" /></p>
    <#else>
        <table class="table">
            <thead>
            <tr>
                <th><@spring.message code="friends.avatar" /></th>
                <th><@spring.message code="friends.name" /></th>
                <th><@spring.message code="friends.note" /></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list friends as friendship>
                <#if friendship.user.id == user.id>
                    <#assign friend = friendship.friend>
                <#else>
                    <#assign friend = friendship.user>
                </#if>
                <tr>
                    <td>
                        <img src="/files/${friend.avatar.storageFileName}" width="40" class="rounded-circle" alt="<@spring.message code='friends.avatar'/>">
                    </td>
                    <td>
                        <a href="/profile/${friend.id}">${friend.firstName} ${friend.lastName}</a>
                    </td>
                    <td>
                        <form method="post" action="/friendships/${friendship.id}/note">
                            <input type="text" name="relationshipNote"
                                   value="${friendship.relationshipNote!''}" maxlength="255">
                            <button class="btn btn-sm btn-outline-primary"><@spring.message code="friends.save" /></button>
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</div>

<#include "fragments/footer.ftlh">
</body>
</html>
