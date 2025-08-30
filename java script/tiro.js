class Tiro extends Objeto {
  constructor(x, y) {
    super(x, y, 20, 40);
    this.vel = 6;
    //this.sprite = new Image();
    //this.sprite.src = "Imagens/tiroNave.gif";
    this.tiroNave = document.getElementById("tiroNave");
  }

  update() {
    this.posY -= this.vel;
  }

  /*draw(ctx) {
    ctx.drawImage(this.sprite, this.posX - this.largura / 2, this.posY, this.largura, this.altura);
  }*/
}
