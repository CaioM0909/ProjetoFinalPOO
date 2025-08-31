class Jogador extends Objeto {
  constructor(x, y, jogo) {
    super(x, y, 150, 150);
    this.jogo = jogo;
    this.vel = 5;
    this.vidas = 3;
    this.cooldown = 0;
    this.nave = document.createElement("img");
    this.nave.classList.add("jogador");
    this.nave.src = "/Imagens/gifNave.gif";
    this.nave.style.position = "absolute";
    this.nave.style.left = this.posX + "px";
    this.nave.style.top = this.posY + "px";
    this.nave.style.width = this.largura + "px";
    this.nave.style.height = this.altura + "px";
    this.nave.onerror = function() {
    console.log("Erro ao carregar imagem:", aa);
    this.style.display = "none"; 
    };
    document.body.appendChild(this.nave);
    
  }

  //função de movimentação da nave do jogador
  update(teclas) {
    
    //mexe a nave para direita quando pressionado
    if (teclas["KeyD"]) { 
        this.posX += this.vel;
        this.nave.style.left = this.posX+"px";
        
    }
    //mexe a nave para esquerda quando pressionado
    if (teclas["KeyA"]) { 
        this.posX -= this.vel;
        this.nave.style.left = this.posX+"px";
    }    
    //atira quando pressionado
    if (teclas["Space"] && this.cooldown <= 0) {
      this.jogo.adicionarTiro(new Tiro(this.posX + this.largura / 2 - 45, this.posY - 10));
      this.cooldown = 40;
    }

    if (this.cooldown > 0) {
      this.cooldown--;
    }
    // Limites da tela
    if (this.posX < -40) {
      this.posX = -40;
    }
    if (this.posX + this.largura - 50 > this.jogo.largura) {
      this.posX = this.jogo.largura - this.largura + 50;
    }
  }

  //remove a nave da tela
  remover() {
      if (this.nave) {
          this.nave.remove();
          this.nave = null;
      } 
  }

  //pega a hitbox da nave do jogador
  getHitbox() {
      return {
          x: this.posX + 180,      
          y: this.posY - 160,      
          largura: this.largura - 70, 
          altura: this.altura -100  
      };
  }

}
