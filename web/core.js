var game = new Phaser.Game(COLS * FONT * WIDTH_TO_HEIGHT_RATIO, ROWS * FONT, Phaser.AUTO, null, {
    create: createGame
});

function createGame() {
    game.input.keyboard.addCallbacks(null, null, onKeyUp);
    initMap();
    initScreen();
    setMapOnScreen();
}

function onKeyUp(event) {
    switch (event.keyCode) {
        case Phaser.Keyboard.LEFT:
        case Phaser.Keyboard.A:

        case Phaser.Keyboard.RIGHT:
        case Phaser.Keyboard.D:

        case Phaser.Keyboard.UP:
        case Phaser.Keyboard.W:

        case Phaser.Keyboard.DOWN:
        case Phaser.Keyboard.S:
    }
}