<!DOCTYPE html>
<#import "spring.ftl" as spring1/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring1.message code="post.create.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<#include "fragments/header.ftlh">

<main class="container flex-grow-1 py-5" style="max-width: 768px;">
    <div class="row justify-content-center">
        <div class="col-md-10 col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center mb-0">
                        <@spring1.message code="post.create.title"/>
                    </h3>
                </div>
                <div class="card-body">
                    <@spring1.bind "postForm"/>
                    <form action="/posts/create" method="post" enctype="multipart/form-data"
                          <#if spring1.status.errors?exists>class="was-validated"</#if> novalidate>

                        <!-- Content -->
                        <div class="mb-3">
                            <label for="content" class="form-label">
                                <@spring1.message code="post.create.contentLabel"/>
                            </label>
                            <@spring1.formInput
                            "postForm.content"
                            "class=\"form-control\" id=\"content\" name=\"content\" rows=\"6\" required maxlength=\"5000\""
                            "textarea"/>
                            <@spring1.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Location -->
                        <div class="mb-3">
                            <label for="location" class="form-label">
                                <@spring1.message code="post.create.locationLabel"/>
                                <span class="text-muted">(<@spring1.message code="post.create.optional"/>)</span>
                            </label>
                            <@spring1.formInput
                            "postForm.location"
                            "class=\"form-control\" id=\"location\" name=\"location\" maxlength=\"255\""
                            "text"/>
                            <@spring1.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Image -->
                        <div class="mb-4">
                            <label for="image" class="form-label">
                                <@spring1.message code="post.create.imageLabel"/>
                                <span class="text-muted">(<@spring1.message code="post.create.optional"/>)</span>
                            </label>
                            <@spring1.formInput
                            "postForm.image"
                            "class=\"form-control\" id=\"image\" name=\"image\" accept=\"image/*\""
                            "file"/>
                            <@spring1.showErrors "<br>" "invalid-feedback d-block"/>
                            <div class="form-text">
                                <@spring1.message code="post.create.imageHint"/>
                            </div>
                        </div>

                        <!-- CSRF & Submit -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="d-grid my-4">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <@spring1.message code="post.create.submit"/>
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
