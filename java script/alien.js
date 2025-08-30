class Alien extends Objeto {
  constructor(x, y, largura, altura) {
    super(x, y, largura, altura);
    this.spriteSheet = spriteSheet;
    this.totalFrames = totalFrames;
    this.currentFrame = 0;
    this.frameCounter = 0;
  }

  update() {
    
    this.frameCounter++;
    if (this.frameCounter % 10 === 0) {
      this.currentFrame = (this.currentFrame + 1) % this.totalFrames;
    }
  }

  draw(ctx) {
    let sx = this.currentFrame * this.largura;
    let sy = 0;
    ctx.drawImage(
      this.spriteSheet,
      sx, sy, this.largura, this.altura,
      this.posX, this.posY, this.largura, this.altura
    );
  }
}
