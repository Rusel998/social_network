<!DOCTYPE html>
<#import "/spring.ftl" as spring1/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring1.message code="admin.users.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<#include "fragments/header.ftlh">
<main class="container py-5" style="max-width: 800px">
    <h2 class="mb-4"><@spring.message code="admin.users.heading"/></h2>
    <#if users?size == 0>
        <div class="alert alert-info"><@spring.message code="admin.users.empty"/></div>
    <#else>
        <table class="table align-middle">
            <thead>
            <tr>
                <th scope="col"><@spring.message code="admin.users.avatar"/></th>
                <th scope="col"><@spring.message code="admin.users.name"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>
                        <img src="${user.avatarUrl}" alt="avatar" width="48" height="48" class="rounded-circle">
                    </td>
                    <td>
                        <a href="/profile/${user.id}" class="text-decoration-none">
                            ${user.firstName} ${user.lastName}
                        </a>
                    </td>
                    <td>
                        <form action="/admin/users/${user.id}/delete" method="post" onsubmit="return confirm('<@spring.message code="admin.users.delete.confirm"/>');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="btn btn-sm btn-danger" type="submit">
                                <@spring.message code="admin.users.delete"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
    <a href="/admin/panel" class="btn btn-outline-secondary mt-3">&larr; <@spring.message code="admin.panel.heading"/></a>
</main>
<#include "fragments/footer.ftlh">
</body>
</html>
