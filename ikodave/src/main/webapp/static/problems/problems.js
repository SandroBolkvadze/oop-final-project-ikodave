
document.addEventListener('DOMContentLoaded', () => {
    addListeners();

    populateToggleGroup({
        url: '/api/problems/difficulties',
        containerId: 'difficulty-toggle',
        groupName: 'difficulty',
        valueKey: 'difficulty',
        labelKey: 'difficulty',
        btnClass: 'btn-outline-primary'
    });

    populateToggleGroup({
        url: '/api/problems/statuses',
        containerId: 'status-toggle',
        groupName: 'status',
        valueKey: 'status',
        labelKey: 'status',
        btnClass: 'btn-outline-primary'
    });

    populateCheckboxGroup({
        url: '/api/problems/topics',
        containerId: 'topics-checkboxes',
        valueKey: 'topic',
        labelKey: 'topic'
    });
});


function addListeners() {
    document.getElementById('filter-button').addEventListener('click', filter);
}

async function filter() {
    const title = document.getElementById('filter-title').value.trim();
    console.log(title);

    const difficultyInput = document.querySelector('#difficulty-toggle input[type="checkbox"]:checked');
    const difficulty = difficultyInput ? difficultyInput.value : null;
    console.log(difficulty);

    const statusInput = document.querySelector('#status-toggle input[type="checkbox"]:checked');
    const status = statusInput ? statusInput.value : null;
    console.log(status);

    const topicCheckboxes = document.querySelectorAll('#topics-checkboxes input[type="checkbox"]:checked');
    const topics = Array.from(topicCheckboxes).map(cb => cb.value);
    console.log(topics);

    let filterCriteria = {
        title: title,
        status: status,
        difficulty: difficulty,
        topics: topics
    };

    const response = await fetch('/api/problems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(filterCriteria)
    });

    console.log(response);
}

async function populateToggleGroup({ url, containerId, groupName, valueKey, labelKey, btnClass = 'btn-outline-primary' }) {
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const items = await res.json();
        const container = document.getElementById(containerId);
        if (!container) return;

        const groupDiv = document.createElement('div');
        groupDiv.className = 'btn-group w-100';
        groupDiv.setAttribute('role', 'group');
        groupDiv.setAttribute('aria-label', groupName);

        items.forEach(item => {
            const value = item[valueKey];
            const label = item[labelKey];

            const inputId = `${groupName}-${value}`;

            const input = document.createElement('input');
            input.type = 'checkbox';
            input.className = 'btn-check';
            input.id = inputId;
            input.value = value;
            input.autocomplete = 'off';

            const labelElem = document.createElement('label');
            labelElem.className = `btn ${btnClass} flex-fill`;
            labelElem.setAttribute('for', inputId);
            labelElem.textContent = label;

            groupDiv.appendChild(input);
            groupDiv.appendChild(labelElem);
        });

        container.appendChild(groupDiv);

        const checkboxes = groupDiv.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(cb => {
            cb.addEventListener('click', function () {
                if (this.checked) {
                    checkboxes.forEach(other => {
                        if (other !== this) other.checked = false;
                    });
                }
            });
        });

    } catch (err) {
        console.error(`Failed to load ${containerId}:`, err);
    }
}



async function populateCheckboxGroup({ url, containerId, valueKey, labelKey }) {
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const items = await res.json();
        const container = document.getElementById(containerId);
        if (!container) return;

        items.forEach(item => {
            const div = document.createElement('div');
            div.className = 'form-check me-2';
            div.innerHTML = `
                <input class="form-check-input topic-checkbox" type="checkbox" value="${item[valueKey]}" id="topic-${item[valueKey]}">
                <label class="form-check-label" for="topic-${item[valueKey]}">${item[labelKey]}</label>
            `;
            container.appendChild(div);
        });
    } catch (err) {
        console.error(`Failed to load ${containerId}:`, err);
    }
}



