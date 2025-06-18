<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message code="register.title"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center"><@spring.message code="register.heading"/></h3>
                </div>
                <div class="card-body">
                    <@spring.bind "userForm"/>
                    <form action="/register" method="post"
                          <#if spring.status.errors?exists>class="was-validated"</#if>>

                        <div class="mb-3">
                            <label for="firstName" class="form-label"><@spring.message code="register.firstName"/></label>
                            <@spring.formInput "userForm.firstName"
                            "class=\"form-control\" id=\"firstName\" name=\"firstName\" required"
                            "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label"><@spring.message code="register.lastName"/></label>
                            <@spring.formInput "userForm.lastName"
                            "class=\"form-control\" id=\"lastName\" name=\"lastName\" required"
                            "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="age" class="form-label"><@spring.message code="register.age"/></label>
                            <@spring.formInput "userForm.age"
                            "class=\"form-control\" id=\"age\" name=\"age\" min=\"1\" max=\"120\" required"
                            "number"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label"><@spring.message code="register.email"/></label>
                            <@spring.formInput "userForm.email"
                            "class=\"form-control\" id=\"email\" name=\"email\" required"
                            "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label"><@spring.message code="register.password"/></label>
                            <@spring.formInput "userForm.password"
                            "class=\"form-control\" id=\"password\" name=\"password\" required"
                            "password"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">
                                <@spring.message code="register.submit"/>
                            </button>
                        </div>
                        <p class="mt-3 text-center">
                            <@spring.message code="register.hasAccount"/>
                            <a href="/login"><@spring.message code="register.loginHere"/></a>.
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
