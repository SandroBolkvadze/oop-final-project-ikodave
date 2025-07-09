
document.addEventListener('DOMContentLoaded', () => {
    addListeners();
    populateCheckboxGroup({
        url: '/api/problems/difficulties',
        containerId: 'difficulty-checkboxes',
        valueKey: 'difficulty',
        labelKey: 'difficulty'
    });
    populateCheckboxGroup({
        url: '/api/problems/statuses',
        containerId: 'status-checkboxes',
        valueKey: 'status',
        labelKey: 'status'
    });
    populateCheckboxGroup({
        url: '/api/problems/topics',
        containerId: 'topics-checkboxes',
        valueKey: 'topic',
        labelKey: 'topic'
    });
});


function addListeners() {
    document.getElementById('filter-button')?.addEventListener('click', filter);
}

async function filter() {
    const title = document.getElementById('filter-title')?.value.trim();
    const difficulty = document.getElementById('filter-difficulty')?.value;
    const status = document.getElementById('filter-status')?.value;

    const topicCheckboxes = document.querySelectorAll('#topics-checkboxes input[type="checkbox"]:checked');
    const topics = Array.from(topicCheckboxes).map(cb => cb.value);

    const filters = { title, difficulty, status, topics };

    try {
        const response = await fetch('/api/problems/filter', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(filters)
        });

        if (!response.ok) throw new Error(`Failed to fetch problems: ${response.status}`);
        const data = await response.json();
        console.log("Filtered problems:", data); // TODO: Render this in your table
    } catch (err) {
        console.error("Filter failed:", err);
    }
}

// --- GENERALIZED LOADER ---
async function populateSelect({ url, selectId, valueKey, labelKey }) {
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const items = await res.json();
        const select = document.getElementById(selectId);
        if (!select) return;

        items.forEach(item => {
            const option = document.createElement('option');
            option.value = item[valueKey];
            option.textContent = item[labelKey];
            select.appendChild(option);
        });
    } catch (err) {
        console.error(`Failed to load ${selectId}:`, err);
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


