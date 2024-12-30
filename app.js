import ToDoService from "./service.js";

function getOrCreateApp(id) {
    let div = document.getElementById(id);

    if (div === null) {
        div = document.createElement('div');
        div.setAttribute('id', id);

        document.body.append(div);
    }

    return div;
}

function createItem(item) {
    const li = document.createElement('li');

    // id
    li.id = item?.id;

    // text
    const p = document.createElement('p');
    p.textContent = item?.text;
    li.appendChild(p);

    // status
    const button = document.createElement('button');
    button.textContent = 'toggle';

    // click event listener for status
    button.addEventListener('click', () => {
        // update status
        item.status = !item?.status;

        // refresh status style
        li.style.backgroundColor = item?.status === true ? 'green' : 'red';

        // TODO: notify new status to backend
        // service.toggleToDoItem(item);
    });

    li.appendChild(button);

    // status style
    li.style.backgroundColor = item?.status === true ? 'green' : 'red';

    // rate
    const div = document.createElement('div');

    // add stars as much as to-do item's rate
    for (let i = 1; i <= item?.rate; i++) {
        // star
        const img = document.createElement('img');

        img.setAttribute('src', './star.png');

        // click event listener
        img.addEventListener('click', () => div.removeChild(img));

        div.appendChild(img);
    }

    li.appendChild(div);

    return li;
}

function createList() {
    return document.createElement('ul');
}

function addItemsToList(list, items) {
    for (let item of items) {
        const li = createItem(item);

        list.appendChild(li);
    }
}

function removeItemsFromList(list) {
    for (let item of list.querySelectorAll('li')) {
        item.remove();
    }
}

function createOption(value, text) {
    const option = document.createElement('option');
    option.setAttribute('value', value);
    option.textContent = text;

    return option;
}

function createDropdown() {
    const select = document.createElement('select');

    const descendingOption = createOption('desc', 'Descending');
    const ascendingOption = createOption('asc', 'Ascending');

    select.append(ascendingOption, descendingOption);

    return select;
}

function sortItemsByRateAscending(items) {
    return items.sort((a, b) => (a.rate > b.rate) ? 1 : ((b.rate > a.rate) ? -1 : 0));
}

function sortItemsByRateDescending(items) {
    return items.sort((a, b) => (b.rate > a.rate) ? 1 : ((a.rate > b.rate) ? -1 : 0));
}

function createSort(service, list, dropdown) {
    const button = document.createElement('button');
    button.textContent = "Sort";

    button.addEventListener('click', () => {
        removeItemsFromList(list);

        const selected = dropdown.options[dropdown.selectedIndex].value;

        service.fetchToDoItems()
            .then(items => {
                let sorted = [];

                if (selected === 'asc') {
                    sorted = sortItemsByRateAscending(items);
                } else {
                    sorted = sortItemsByRateDescending(items);
                }

                addItemsToList(list, sorted);
            });
    });

    return button;
}

const service = new ToDoService();

const app = getOrCreateApp('toDoApp');

const dropdown = createDropdown();
app.append(dropdown);

const list = createList();

const sort = createSort(service, list, dropdown);
app.append(sort);

app.append(list);

service.fetchToDoItems()
    .then(items => addItemsToList(list, items));