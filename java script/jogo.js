class Jogo {
  constructor(canvasId) {
    this.canvas = document.getElementById(canvasId);
    this.ctx = this.canvas.getContext("2d");

    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    this.objetos = []; // todos os objetos: jogador, aliens, tiros
    this.teclas = {};

    window.addEventListener("resize", () => this.redimensionaCanvas());
    window.addEventListener("keydown", e => this.teclas[e.code] = true);
    window.addEventListener("keyup", e => this.teclas[e.code] = false);

    this.loop = this.loop.bind(this);
  }

  redimensionaCanvas() {
    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    const jogador = this.objetos.find(e => e instanceof Jogador);
    if (jogador) {
      jogador.posY = this.altura - jogador.altura - 20;
    }
  }

  adicionarTiro(tiro) {
    this.objetos.push(tiro);
  }

  iniciar() {
    const jogador = new Jogador(this.largura / 2, this.altura - 120, this);
    this.objetos.push(jogador);
    this.criarAliens();
    nave.style.visibility = "visible";

    

    /*const spriteSheet = new Image();
    spriteSheet.src = "alienS1.png";*/

    /*spriteSheet.onload = () => {
    // cria os aliens e inicia o loop
    this.criarAliens(spriteSheet);*/
    requestAnimationFrame(this.loop);
    
  }

  criarAliens() {
    
    let x = 100;
    for (let i = 0; i < 12; i++) {
      this.objetos.push(new Alien(x, 50, 500, 500));
      x += 80;
    }
  }

  loop() {
  this.ctx.clearRect(0, 0, this.largura, this.altura);

  this.alienMudouDirecao = false;
  this.contadorAliens++;
  // Atualiza todos os objetos
    this.objetos.forEach(objeto => {
      if (objeto instanceof Alien) {
        if(objeto.posX + objeto.largura >= jogo.largura+250 || objeto.posX <= -250) {
          // muda direção global
          jogo.direcaoAliens *= -1;
          // desce todos os aliens
          jogo.objetos.forEach(obj => {
            if(obj instanceof Alien) {
              obj.posY += 50; // distância para descer
            }
          }
          );
         }
          if (this.contadorAliens >= this.delayAliens) {
            objeto.update(this.teclas, this);
          }
      }

      else {
          //Jogador, tiros etc...
          objeto.update(this.teclas, this);
          
        }
    });

  if (this.contadorAliens >= this.delayAliens) 
    this.contadorAliens = 0;

  // Remove tiros que saíram da tela
  this.objetos = this.objetos.filter(objeto => {
    if (objeto instanceof Tiro && objeto.posY + objeto.altura < 0) {
      objeto.remover(); // remove o <img> da tela
      return false; // tira do array
    }
    return true;

    
  });

  this.objetos.forEach(objeto1 => {
  if (objeto1 instanceof Tiro) {
    this.objetos.forEach(objeto2 => {
      if (objeto2 instanceof Alien) {
        if (this.colidiu(objeto1, objeto2)) {
          // remove o alien
          if (objeto2.alien) 
            objeto2.alien.remove();
          // remove o tiro
          objeto1.remover();

          // marca para remoção no próximo filtro
          objeto1.morto = true;
          objeto2.morto = true;
        }
      }
    });
  }
});

  // Remove objetos que estão mortos ou fora da tela
  this.objetos = this.objetos.filter(objeto => {
    if (objeto.morto) return false;

    if (objeto instanceof Tiro && objeto.posY + objeto.altura < 0) {
      objeto.remover();
      return false;
    }
    return true;
  });

  requestAnimationFrame(this.loop);
  }

  colidiu(a, b) {
  let boxA = a.getHitbox ? a.getHitbox() : a;
  let boxB = b.getHitbox ? b.getHitbox() : b;

  return (
    boxA.x < boxB.x + boxB.largura &&
    boxA.x + boxA.largura > boxB.x &&
    boxA.y < boxB.y + boxB.altura &&
    boxA.y + boxA.altura > boxB.y
  );
}
}
