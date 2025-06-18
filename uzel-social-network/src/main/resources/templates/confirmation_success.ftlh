<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring.message code="email.confirm.success.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<div class="container d-flex justify-content-center align-items-center flex-grow-1" style="min-height: 80vh;">
    <div class="card shadow p-4 text-center" style="max-width: 420px; width: 100%;">
        <div class="mb-4">
            <i class="bi bi-check-circle-fill text-success" style="font-size: 3rem;"></i>
        </div>
        <h1 class="mb-3"><@spring.message code="email.confirm.success.header"/></h1>
        <p class="mb-4"><@spring.message code="email.confirm.success.text"/></p>
        <a href="/login" class="btn btn-primary"><@spring.message code="email.confirm.success.loginBtn"/></a>
    </div>
</div>
</body>
</html>
