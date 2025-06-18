<!DOCTYPE html>
<#import "/spring.ftl" as spring1 />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring1.message code="main.title" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<#include "fragments/header.ftlh">

<main class="container flex-grow-1 py-5 d-flex align-items-center justify-content-center">
    <#if user??>
        <div class="row w-100 justify-content-center align-items-center">
            <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                <h1 class="display-4 fw-bold mb-3">
                    <@spring.message code="main.auth.heading"/>
                </h1>
                <p class="lead mb-4">
                    ${springMacroRequestContext.getMessage("main.auth.description", [user.firstName])}
                </p>
            </div>
            <div class="col-lg-5 text-center">
                <img src="/images/banner-auth.png" alt="<@spring.message code='main.img.alt'/>"
                     class="img-fluid rounded-4 shadow-sm" style="max-height: 330px;">
            </div>
        </div>
    <#else>
        <div class="row w-100 justify-content-center align-items-center">
            <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                <h1 class="display-4 fw-bold mb-3">
                    <@spring.message code="main.heading"/>
                </h1>
                <p class="lead mb-4">
                    <@spring.message code="main.description"/>
                </p>
                <div>
                    <a href="/register" class="btn btn-primary btn-lg me-2 mb-2">
                        <@spring.message code="main.register"/>
                    </a>
                    <a href="/login" class="btn btn-outline-secondary btn-lg mb-2">
                        <@spring.message code="main.login"/>
                    </a>
                </div>
            </div>
            <div class="col-lg-5 text-center">
                <img src="/images/banner.png" alt="<@spring.message code='main.img.alt'/>"
                     class="img-fluid rounded-4 shadow-sm" style="max-height: 330px;">
            </div>
        </div>
    </#if>
</main>


<#include "fragments/footer.ftlh">

</body>
</html>
