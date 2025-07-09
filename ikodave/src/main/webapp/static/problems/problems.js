
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

    const problems = await response.json();

    const tbody = document.getElementById('problems-table-body');
    tbody.innerHTML = '';

    problems.forEach((p, idx) => {
        const tr = document.createElement('tr');

        const tdIndex = document.createElement('td');
        tdIndex.textContent = idx + 1;
        tr.appendChild(tdIndex);

        const tdName = document.createElement('td');
        tdName.textContent = p.title;
        tr.appendChild(tdName);

        const tdDiff = document.createElement('td');
        const diffBadge = document.createElement('span');
        diffBadge.textContent = p.difficultyName;
        diffBadge.className = `badge ${
            p.difficultyName.toLowerCase() === 'easy'   ? 'btn-easy'   :
                p.difficultyName.toLowerCase() === 'medium' ? 'btn-medium' :
                    'btn-hard'
        }`;
        tdDiff.appendChild(diffBadge);
        tr.appendChild(tdDiff);

        const tdStatus = document.createElement('td');
        const statusBadge = document.createElement('span');
        statusBadge.textContent = p.status;
        statusBadge.className = `badge ${
            p.status.toLowerCase() === 'accepted' ? 'bg-success' : 'bg-secondary'
        }`;
        tdStatus.appendChild(statusBadge);
        tr.appendChild(tdStatus);

        const tdTopics = document.createElement('td');
        tr.appendChild(tdTopics);

        const tdLink = document.createElement('td');
        const a = document.createElement('a');
        a.href = `/problems/${encodeURIComponent(p.title)}`;
        a.className = 'btn btn-sm btn-outline-primary';
        a.textContent = 'View';
        tdLink.appendChild(a);
        tr.appendChild(tdLink);

        tbody.appendChild(tr);
    });
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



