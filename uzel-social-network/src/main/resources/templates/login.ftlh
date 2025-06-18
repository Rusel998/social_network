<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message code="login.title"/></title>
    <!-- Подключаем Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header text-center">
                    <h3><@spring.message code="login.heading"/></h3>
                </div>
                <div class="card-body">
                    <form action="/login" method="post">

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="mb-3">
                            <label for="email" class="form-label"><@spring.message code="login.email"/></label>
                            <input type="email"
                                   id="email"
                                   name="email"
                                   class="form-control"
                                   required
                                   autofocus>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label"><@spring.message code="login.password"/></label>
                            <input type="password"
                                   id="password"
                                   name="password"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary"><@spring.message code="login.submit"/></button>
                        </div>
                    </form>

                    <!-- Кнопка входа через Google -->
                    <div class="d-grid mt-3">
                        <a href="/oauth2/authorization/google" class="btn btn-outline-danger">
                            <@spring.message code="login.google"/>
                        </a>
                    </div>

                    <p class="mt-3 text-center">
                        <@spring.message code="login.noAccount"/>
                        <a href="/register"><@spring.message code="login.registerHere"/></a>.
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
