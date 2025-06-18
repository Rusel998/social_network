<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring.message code="error.403.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center vh-100">
<div class="text-center">
    <img src="/images/403.png" alt="403" class="img-fluid mb-4" style="max-width: 300px;">
    <h2 class="mb-3"><@spring.message code="error.403.heading"/></h2>
    <a href="/" class="btn btn-primary">
        <@spring.message code="error.back.to.home"/>
    </a>
</div>
</body>
</html>
