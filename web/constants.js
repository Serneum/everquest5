// Font size
var FONT = 32;
var WIDTH_TO_HEIGHT_RATIO = 0.6;

// Map dimensions
var ROWS = 24;
var COLS = 40;

// We build our maps using cellular automata
var CHANCE_TO_START_ALIVE = 0.65;
var DEATH_LIMIT = 2;
var BIRTH_LIMIT = 3;
var OVERCROWDING_LIMIT = 5;
var CYCLES = 10;

// Number of actors per level, including player. Minimum 10, max 100
var ACTORS = randomIntFromInterval(10, 100);

function randomIntFromInterval(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}