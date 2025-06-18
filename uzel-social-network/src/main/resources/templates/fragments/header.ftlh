<#import "/spring.ftl" as spring />

<header class="header bg-white shadow-sm py-3 px-4 d-flex align-items-center justify-content-between">
    <a href="/" class="d-flex align-items-center">
        <img
                src="/images/logo.png"
                alt="<@spring.message code='site.logo.alt'/>"
                style="height: 60px;"
                class="logo_of_the_site me-3"/>
    </a>

    <#if user??>
        <div class="position-relative flex-grow-1 mx-4">
            <input
                    id="search-input"
                    class="form-control me-2"
                    type="search"
                    placeholder="<@spring.message code='search.placeholder'/>"
                    autocomplete="off"
            >
            <div id="search-results" class="list-group position-absolute w-100 mt-1" style="z-index: 1000;"></div>
        </div>
    </#if>

    <ul class="nav align-items-center mb-0">
        <li class="nav-item">
            <a class="nav-link text-dark"
               href="/">
                <@spring.message code="nav.home"/>
            </a>
        </li>

        <#if user??>
            <li class="nav-item">
                <a class="nav-link text-dark"
                   href="https://t.me/iblameuzel">
                    <@spring.message code="nav.contact"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-dark"
                   href="/profile/${user.id}">
                    <@spring.message code="nav.profile"/>
                </a>
            </li>
            <#if user.role == "ADMIN">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/panel"><@spring.message code="nav.adminPanel"/></a>
                </li>
            </#if>
        <li class="nav-item">
            <a class="nav-link text-dark"
               href="/posts/create">
                <@spring.message code="nav.createPost"/>
            </a>
        </li>
    <#else>
        <li class="nav-item">
            <a class="nav-link text-dark"
               href="https://t.me/iblameuzel">
                <@spring.message code="nav.contact"/>
            </a>
        </li>
    </#if>

        <li class="nav-item">
            <select id="langSelect" class="form-select form-select-sm">
                <option value="en" <#if lang == "en">selected</#if>>English</option>
                <option value="ru" <#if lang == "ru">selected</#if>>Русский</option>
            </select>
        </li>
    </ul>
</header>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(function() {
        $('#langSelect').on('change', function() {
            const lang = this.value;
            localStorage.setItem('lang', lang);
            document.cookie = "locale=" + lang + "; path=/";
            location.reload();
        });

        $('#search-input').on('input', function () {
            let query = $(this).val().trim();
            if (query.length < 2) {
                $('#search-results').empty();
                return;
            }

            $.ajax({
                url: '/api/users/search',
                method: 'GET',
                data: { query: query },
                success: function (users) {
                    if (users.length === 0) {
                        $('#search-results').html('<div class="list-group-item text-muted">Ничего не найдено</div>');
                        return;
                    }

                    let results = users.map(function(user) {
                        return '<a href="/profile/' + user.id + '" class="list-group-item list-group-item-action d-flex align-items-center">' +
                            '<img src="' + user.avatarUrl + '" width="32" height="32" class="rounded-circle me-2">' +
                            user.firstName + ' ' + user.lastName +
                            '</a>';
                    }).join('');
                    $('#search-results').html(results);
                }
            });
        });

        $(document).on('click', function (e) {
            if (!$(e.target).closest('#search-input, #search-results').length) {
                $('#search-results').empty();
            }
        });
    });
</script>
