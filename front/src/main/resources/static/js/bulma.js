const dropdown = document.querySelector('.dropdown');
if (dropdown) {
    dropdown.addEventListener('click', function (event) {
        event.stopPropagation();
        dropdown.classList.toggle('is-active');
    });
}


function makeTemplate(templateId, data) {
    return document.getElementById(templateId).innerHTML
        .replace(
            /%(\w*)%/g, // or /{(\w*)}/g for "{this} instead of %this%"
            function (m, key) {
                return data.hasOwnProperty(key) ? data[key] : "";
            }
        );
}
