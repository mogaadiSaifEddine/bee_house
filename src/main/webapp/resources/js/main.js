// Map initialization
let map;
let marker;

function initMap() {
    // Initialize the map
    map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 46.227638, lng: 2.213749 }, // Center of France
        zoom: 6
    });

    // Add click listener to map
    map.addListener('click', function (event) {
        placeMarker(event.latLng);
        updateCoordinates(event.latLng);
    });

    // Initialize marker if coordinates are provided
    const latInput = document.getElementById('latitude');
    const lngInput = document.getElementById('longitude');

    if (latInput && lngInput && latInput.value && lngInput.value) {
        const latLng = {
            lat: parseFloat(latInput.value),
            lng: parseFloat(lngInput.value)
        };
        placeMarker(latLng);
        map.setCenter(latLng);
        map.setZoom(12);
    }
}

function placeMarker(location) {
    if (marker) {
        marker.setPosition(location);
    } else {
        marker = new google.maps.Marker({
            position: location,
            map: map,
            draggable: true
        });

        // Add drag listener to marker
        marker.addListener('dragend', function (event) {
            updateCoordinates(event.latLng);
        });
    }
}

function updateCoordinates(latLng) {
    const latInput = document.getElementById('latitude');
    const lngInput = document.getElementById('longitude');

    if (latInput && lngInput) {
        latInput.value = latLng.lat().toFixed(6);
        lngInput.value = latLng.lng().toFixed(6);
    }
}

// Form validation
document.addEventListener('DOMContentLoaded', function () {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function (event) {
            if (!validateForm(this)) {
                event.preventDefault();
            }
        });
    });
});

function validateForm(form) {
    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            showError(field, 'This field is required');
        } else {
            clearError(field);
        }
    });

    // Validate numeric fields
    const numericFields = form.querySelectorAll('input[type="number"]');
    numericFields.forEach(field => {
        if (field.value) {
            const value = parseFloat(field.value);
            if (isNaN(value)) {
                isValid = false;
                showError(field, 'Please enter a valid number');
            } else if (field.min && value < parseFloat(field.min)) {
                isValid = false;
                showError(field, `Value must be at least ${field.min}`);
            } else if (field.max && value > parseFloat(field.max)) {
                isValid = false;
                showError(field, `Value must be at most ${field.max}`);
            }
        }
    });

    return isValid;
}

function showError(field, message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    errorDiv.style.color = '#e74c3c';
    errorDiv.style.fontSize = '12px';
    errorDiv.style.marginTop = '5px';

    field.style.borderColor = '#e74c3c';

    const parent = field.parentElement;
    const existingError = parent.querySelector('.error-message');
    if (existingError) {
        parent.removeChild(existingError);
    }
    parent.appendChild(errorDiv);
}

function clearError(field) {
    field.style.borderColor = '#ddd';
    const parent = field.parentElement;
    const errorDiv = parent.querySelector('.error-message');
    if (errorDiv) {
        parent.removeChild(errorDiv);
    }
}

// Delete confirmation
document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.btn-danger');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            if (!confirm('Are you sure you want to delete this item?')) {
                event.preventDefault();
            }
        });
    });
});

// Dynamic form fields
document.addEventListener('DOMContentLoaded', function () {
    const hiveTypeSelect = document.querySelector('#type');
    if (hiveTypeSelect) {
        hiveTypeSelect.addEventListener('change', function () {
            updateHiveFormFields(this.value);
        });
    }
});

function updateHiveFormFields(hiveType) {
    const colonySizeField = document.querySelector('#colonySize');
    const honeyQuantityField = document.querySelector('#honeyQuantity');

    switch (hiveType) {
        case 'LANGSTROTH':
            colonySizeField.max = '100';
            honeyQuantityField.max = '45';
            break;
        case 'DADANT':
            colonySizeField.max = '80';
            honeyQuantityField.max = '35';
            break;
        case 'WARRÉ':
            colonySizeField.max = '60';
            honeyQuantityField.max = '25';
            break;
        default:
            colonySizeField.max = '100';
            honeyQuantityField.max = '45';
    }
}

// Table sorting
document.addEventListener('DOMContentLoaded', function () {
    const tables = document.querySelectorAll('.data-table');
    tables.forEach(table => {
        const headers = table.querySelectorAll('th');
        headers.forEach(header => {
            if (header.dataset.sortable !== 'false') {
                header.style.cursor = 'pointer';
                header.addEventListener('click', function () {
                    sortTable(table, Array.from(headers).indexOf(this));
                });
            }
        });
    });
});

function sortTable(table, column) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    const isNumeric = !isNaN(rows[0].children[column].textContent);

    rows.sort((a, b) => {
        const aValue = a.children[column].textContent;
        const bValue = b.children[column].textContent;

        if (isNumeric) {
            return parseFloat(aValue) - parseFloat(bValue);
        }
        return aValue.localeCompare(bValue);
    });

    const currentDirection = table.dataset.sortDirection === 'asc' ? 'desc' : 'asc';
    if (currentDirection === 'desc') {
        rows.reverse();
    }

    table.dataset.sortDirection = currentDirection;

    rows.forEach(row => tbody.appendChild(row));
}

// Responsive table
document.addEventListener('DOMContentLoaded', function () {
    const tables = document.querySelectorAll('.data-table');
    tables.forEach(table => {
        if (window.innerWidth <= 768) {
            makeTableResponsive(table);
        }
    });

    window.addEventListener('resize', function () {
        tables.forEach(table => {
            if (window.innerWidth <= 768) {
                makeTableResponsive(table);
            } else {
                makeTableNormal(table);
            }
        });
    });
});

function makeTableResponsive(table) {
    const headers = Array.from(table.querySelectorAll('th')).map(th => th.textContent);
    const rows = table.querySelectorAll('tbody tr');

    rows.forEach(row => {
        const cells = row.querySelectorAll('td');
        cells.forEach((cell, index) => {
            cell.setAttribute('data-label', headers[index]);
        });
    });

    table.classList.add('responsive');
}

function makeTableNormal(table) {
    const cells = table.querySelectorAll('td');
    cells.forEach(cell => {
        cell.removeAttribute('data-label');
    });

    table.classList.remove('responsive');
}

// Add error styling
const style = document.createElement('style');
style.textContent = `
    .error {
        border-color: var(--danger-color) !important;
    }
    .error:focus {
        box-shadow: 0 0 0 2px rgba(244, 67, 54, 0.2) !important;
    }
`;
document.head.appendChild(style);

// Add form validation to all forms
document.addEventListener('DOMContentLoaded', function () {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function (event) {
            if (!validateForm(this)) {
                event.preventDefault();
                alert('Please fill in all required fields.');
            }
        });
    });

    // Initialize map if map container exists
    if (document.getElementById('map')) {
        // Load Google Maps API
        const script = document.createElement('script');
        script.src = `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}&callback=initMap`;
        script.async = true;
        script.defer = true;
        document.head.appendChild(script);
    }
}); 