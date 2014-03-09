var screen;

function initScreen() {
    screen = [];
    for (var i = 0; i < COLS; i++) {
        var row = [];
        for (var j = 0; j < ROWS; j++) {
            row.push(initCell('', i, j));
        }
        screen.push(row);
    }
}