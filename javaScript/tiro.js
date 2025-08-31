class Tiro extends Objeto {
  constructor(x, y) {
    super(x, y, 90, 180);
    //velocidade do tiro 
    this.vel = 8;

    //cria a imagem do tiro 
    this.element = document.createElement("img");
    this.element.src = "/Imagens/tiroNave.gif";
    this.element.style.position = "absolute";
    this.element.style.width = this.largura + "px";
    this.element.style.height = this.altura + "px";
    this.element.style.left = x + "px";
    this.element.style.top = y + "px";
    this.element.onerror = function() {
    console.log("Erro ao carregar imagem:", aa);
    this.style.display = "none"; 
    };
    document.body.appendChild(this.element);
  }

  //isso faz o tiro se mover de baixo para cima
  update() {
    this.posY -= this.vel;
    this.element.style.top = this.posY + "px";
  }
  //remove o tiro da tela
  remover() {
    this.element.remove();
  }
  //pega a hitbox do tiro
  getHitbox() {
    return {
      x: this.posX + 20,  
      y: this.posY + 40,
      largura: 4,
      altura: this.altura - 200
    };
  }
  
  
}
