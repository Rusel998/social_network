<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring.message code="email.confirm.fail.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<div class="container d-flex justify-content-center align-items-center flex-grow-1" style="min-height: 80vh;">
    <div class="card shadow p-4 text-center" style="max-width: 420px; width: 100%;">
        <div class="mb-4">
            <i class="bi bi-x-circle-fill text-danger" style="font-size: 3rem;"></i>
        </div>
        <h1 class="mb-3"><@spring.message code="email.confirm.fail.header"/></h1>
        <p class="mb-4"><@spring.message code="email.confirm.fail.text"/></p>
        <a href="/" class="btn btn-outline-secondary"><@spring.message code="email.confirm.fail.homeBtn"/></a>
    </div>
</div>
</body>
</html>
