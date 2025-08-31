class TiroAlien extends Objeto {
  constructor(x, y) {
    super(x, y, 120, 200);

    //cria a imagem do tiro alien
    this.element = document.createElement("img");
    const randomiza = Math.floor(Math.random() * 20);
    if(randomiza<=9){
        this.element.src = "/Imagens/tiroRapido.gif";
        this.vel = 3;
    }
    if(randomiza>9 && randomiza<17){
        this.element.src = "/Imagens/tiroMedio.gif";
        this.vel = 2;
    }
    if(randomiza>=17){
        this.element.src = "/Imagens/tiroDevagar.gif";
        this.vel = 1.5;
    }
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
  
  //isso faz o tiro alien se mover de cima para baixo
  update() {
    this.posY += this.vel;
    this.element.style.top = this.posY + "px";
  }
  //remove o tiro alien da tela
  remover() {
    this.element.remove();
  }
  //pega o hitbox do tiro alien
  getHitbox() {
    return {
      x: this.posX + 200,   
      y: this.posY -150,
      largura: 4,
      altura: this.altura  -180
    };
  }
}