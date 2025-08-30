class Tiro extends Objeto {
  constructor(x, y) {
    super(x, y, 90, 180);
    this.vel = 5 ;

    // cria um <img> para este tiro
    this.element = document.createElement("img");
    this.element.src = "Imagens/tiroNave.gif";
    this.element.style.position = "absolute";
    this.element.style.width = this.largura + "px";
    this.element.style.height = this.altura + "px";
    this.element.style.left = x + "px";
    this.element.style.top = y + "px";
    document.body.appendChild(this.element); // adiciona na tela
  }

  update() {
    this.posY -= this.vel;
    this.element.style.top = this.posY + "px";
  }

  remover() {
    this.element.remove(); // remove da tela
  }
}
