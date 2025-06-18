<!DOCTYPE html>
<#import "spring.ftl" as spring1/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring1.message code="profile.edit.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<#include "fragments/header.ftlh">

<main class="container flex-grow-1 py-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center mb-0"><@spring.message code="profile.edit.title"/></h3>
                </div>
                <div class="card-body">
                    <@spring.bind "profileForm"/>
                    <form action="/profile/edit" method="post" enctype="multipart/form-data"
                          <#if spring.status.errors?exists>class="was-validated"</#if> novalidate>

                        <div class="mb-3">
                            <label for="firstName" class="form-label"><@spring.message code="profile.edit.firstName.label"/></label>
                            <@spring.formInput "profileForm.firstName"
                            "class=\"form-control\" id=\"firstName\" name=\"firstName\" required minlength=\"2\" maxlength=\"50\""
                            "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback "/>
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label"><@spring.message code="profile.edit.lastName.label"/></label>
                            <@spring.formInput "profileForm.lastName"
                            "class=\"form-control\" id=\"lastName\" name=\"lastName\" required minlength=\"2\" maxlength=\"50\""
                            "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="age" class="form-label"><@spring.message code="profile.edit.age.label"/></label>
                            <@spring.formInput "profileForm.age"
                            "class=\"form-control\" id=\"age\" name=\"age\" required min=\"1\" max=\"120\""
                            "number"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-3">
                            <label for="bio" class="form-label">
                                <@spring.message code="profile.edit.bio.label"/>
                                <span class="text-muted">(<@spring.message code="profile.edit.bio.optional"/>)</span>
                            </label>
                            <@spring.formInput "profileForm.bio"
                            "class=\"form-control\" id=\"bio\" name=\"bio\" rows=\"5\" maxlength=\"1000\""
                            "textarea"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <div class="mb-4">
                            <label for="avatarFile" class="form-label"><@spring.message code="profile.edit.avatar.label"/></label>
                            <@spring.formInput "profileForm.avatarFile"
                            "class=\"form-control\" id=\"avatarFile\" name=\"avatarFile\" accept=\"image/*\""
                            "file"/>
                            <@spring.showErrors "<br>" "invalid-feedback d-block"/>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="d-grid my-4">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <@spring.message code="profile.edit.save"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<#include "fragments/footer.ftlh">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
