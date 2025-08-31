class Alien extends Objeto {
  constructor(x, y, largura, altura , caminho) {
    super(x, y, largura, altura);
    this.alien = document.createElement("img");
    this.alien.src = caminho;
    this.alien.classList.add("alien");
    this.alien.style.position = "absolute";
    this.alien.style.left = this.posX + "px";
    this.alien.style.top = this.posY + "px";
    this.alien.style.width = this.largura + "px";
    this.alien.style.height = this.altura + "px";
    this.alien.onerror = function() {
    console.log("Erro ao carregar imagem:", aa);
    this.style.display = "none"; 
    };
    document.body.appendChild(this.alien);
    
  }
  //pega a hitbox do alien
  getHitbox() {
    return {
      x: this.posX + 180,      
      y: this.posY - 160,      
      largura: this.largura - 420, 
      altura: this.altura - 100   
    };
  }
  //remove o alien da tela 
  remover() {
    if (this.alien) {
      this.alien.remove();
      this.alien = null;
    }
  }
}



class Alien1 extends Alien{
    constructor(x, y, largura, altura) {
        super(x, y, largura, altura,"/Imagens/alien1.gif");
        this.tipo = "alien1";
    }
}
class Alien2 extends Alien{
    constructor(x, y, largura, altura) {
        super(x, y, largura, altura,"/Imagens/alien2.gif");
        this.tipo = "alien2";
    }
}
class Alien3 extends Alien{
    constructor(x, y, largura, altura) {
        super(x, y, largura, altura,"/Imagens/alien3.gif");
        this.tipo = "alien3";
    }
}
  
