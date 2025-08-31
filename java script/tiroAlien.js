class TiroAlien extends Objeto {
  constructor(x, y) {
    super(x, y, 120, 200);

    this.element = document.createElement("img");
    const randomiza = Math.floor(Math.random() * 20);
    if(randomiza<=9){
        this.element.src = "/ProjetoFinal/Imagens/tiroRapido.gif";
        this.vel = 2;
    }
    if(randomiza>9 && randomiza<17){
        this.element.src = "/ProjetoFinal/Imagens/tiroMedio.gif";
        this.vel = 1.5;
    }
    if(randomiza>=17){
        this.element.src = "/ProjetoFinal/Imagens/tiroDevagar.gif";
        this.vel = 1;
    }
    this.element.style.position = "absolute";
    this.element.style.width = this.largura + "px";
    this.element.style.height = this.altura + "px";
    this.element.style.left = x + "px";
    this.element.style.top = y + "px";
    document.body.appendChild(this.element);
  }

  update() {
    this.posY += this.vel;
    this.element.style.top = this.posY + "px";
  }

  remover() {
    this.element.remove();
  }

  getHitbox() {
    return {
      x: this.posX + 200,   // deixa a hitbox mais estreita
      y: this.posY -150,
      largura: 4,
      altura: this.altura  -180
    };
  }
}