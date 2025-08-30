class Jogador extends Objeto {
  constructor(x, y, jogo) {
    super(x, y, 100, 100);
    this.jogo = jogo;
    this.vel = 3;
    this.cooldown = 0;
    this.nave = document.getElementById("nave");
    /*this.sprite = new Image();
    this.sprite.src = "Imagens/gifNave.gif";*/
    
  }

  update(teclas) {
    

    if (teclas["KeyD"]) { 
        this.posX += this.vel;
        nave.style.left = this.posX+"px";
        
    }
    if (teclas["KeyA"]) { 
        this.posX -= this.vel;
        nave.style.left = this.posX+"px";
    }    
    if (teclas["Space"] && this.cooldown <= 0) {
      this.jogo.adicionarTiro(new Tiro(this.posX + this.largura / 2 - 20, this.posY - 10));
      this.cooldown = 70;
    }

    if (this.cooldown > 0) {
      this.cooldown--;
    }
    // Limites da tela
    if (this.posX < 0) {
      this.posX = 0;
    }
    if (this.posX + this.largura > this.jogo.largura) {
      this.posX = this.jogo.largura - this.largura;
    }
  }

  /*draw(ctx) {
    ctx.drawImage(this.sprite, this.posX, this.posY, this.largura, this.altura);
  }*/
}
