<!DOCTYPE html>
<#import "/spring.ftl" as spring1/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring1.message code="admin.panel.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<#include "fragments/header.ftlh">
<main class="container py-5" style="max-width: 600px">
    <h2 class="mb-4"><@spring.message code="admin.panel.heading"/></h2>
    <ul class="list-group">
        <li class="list-group-item">
            <a href="/admin/users" class="btn btn-link">
                <@spring.message code="admin.panel.users"/>
            </a>
        </li>
    </ul>
</main>
<#include "fragments/footer.ftlh">
</body>
</html>
