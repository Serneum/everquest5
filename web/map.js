var map;

function initMap() {
    map = [];
    for (var i = 0; i < COLS; i++) {
        var row = [];
        for (var j = 0; j < ROWS; j++) {
            if (Math.random() < CHANCE_TO_START_ALIVE) {
                row.push(true);
            }
            else {
                row.push(false);
            }
        }
        map.push(row);
    }

    evolveMap(map, CYCLES);
}

function evolveMap(oldMap, cycles) {
    if (cycles === 0) {
        return;
    }

    map = [];
    for (var i = 0; i < COLS; i++) {
        var row = [];
        for (var j = 0; j < ROWS; j++) {
            var neighbors = countAliveNeighbors(oldMap, i, j);
            if (oldMap[i][j]) {
                if (neighbors < DEATH_LIMIT) {
                    row.push(false);
                }
                else if (neighbors > OVERCROWDING_LIMIT) {
                    row.push(false);
                }
                else {
                    row.push(true);
                }
            }
            else {
                if (neighbors == BIRTH_LIMIT) {
                    row.push(true);
                }
                else {
                    row.push(false);
                }
            }
        }
        map.push(row);
    }
    evolveMap(map, cycles - 1);
}

function setMapOnScreen() {
    for (var i = 0; i < COLS; i++) {
        for (var j = 0; j < ROWS; j++) {
            screen[i][j].content = map[i][j] ? "." : "#";
        }
    }
}

function initCell(sym, x, y) {
    var style = {
        font: FONT + "px monospace",
        fill: "#FFFFFF"
    };
    return game.add.text(FONT * WIDTH_TO_HEIGHT_RATIO * x, FONT * y, sym, style);
}

function countAliveNeighbors(map, x, y) {
    var count = 0;

    for (var i = x - 1; i <= x + 1; i++) {
        for (var j = y - 1; j <= y + 1; j++) {
            if (i == x || j == y) {
                continue;
            }
            else if (i < 0 || j < 0 || i >= COLS || j >= ROWS) {
                count++;
            }
            else if (map[i][j]) {
                count++;
            }
        }
    }

    return count;
}