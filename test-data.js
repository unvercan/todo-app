import ToDoItem from "./item.js";

let other = {};
other.id = 3;
other.text = "interview practice";
other.status = false;
other.rate = 5;

let another = {
    id: 4,
    text: 'make a project',
    status: true,
    rate: 10
};

const testItems = [
    new ToDoItem(1, "buy milk", true, 3),
    new ToDoItem(2, "buy chocolate", false, 4),
    other,
    another
];

export default testItems;