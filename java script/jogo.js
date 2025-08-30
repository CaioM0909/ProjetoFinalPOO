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

    this.direcaoAliens = 1;
    this.velAliens = 50;
    this.contadorAliens = 0; 
    this.contadorDescida=0;
    this.delayDescida=100;
    this.delayAliens = 200;
    this.descendo=false;
    this.desceu=0

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
        let y=100;
        for(let j=0;j<5;j++){
            for(let i = 0; i < 12; i++) {
                if(j<2){
                    this.objetos.push(new Alien(x, y, 500, 500,"/ProjetoFinal/Imagens/alien1.gif"));
                }
                if(j>=2 && j<=3){
                    this.objetos.push(new Alien(x, y, 500, 500,"/ProjetoFinal/Imagens/alien2.gif"));
                }
                if(j==4){
                    this.objetos.push(new Alien(x, y, 500, 500,"/ProjetoFinal/Imagens/alien3.gif"));
                }
                x += 80;
            }
            y-=50;
            x=100;
        }
    }

  loop() {
  this.ctx.clearRect(0, 0, this.largura, this.altura);

  this.descendo=false;
  this.alienMudouDirecao = false;
  this.contadorAliens++;
  if (this.contadorAliens >= this.delayAliens) {
    // Filtra apenas os aliens
        const aliens = this.objetos.filter(o => o instanceof Alien);

        // Calcula posição mínima e máxima dos aliens
        const maxX = Math.max(...aliens.map(a => a.posX + a.largura-160));
          const minX = Math.min(...aliens.map(a => a.posX+160));

        // Se algum alien atingir a borda, muda direção e desce todos
        if (maxX >= this.largura || minX <= 0) {
            this.direcaoAliens *= -1;
            this.descendo=true;
            //Faz os aliens descerem
            aliens.forEach(a => {
            a.posY += 50; 
            });
        }
  // Move todos os aliens
        aliens.forEach(a => {
            if(this.desceu==0){
                a.posX += this.velAliens * this.direcaoAliens;
            }
            
            // Atualiza posição do elemento <img>
            if (a.alien) {
                if(this.descendo==false){
                    a.alien.style.left = a.posX + "px";
                }
                a.alien.style.top = a.posY + "px";
            }
          });
            //Para fazer com que a posição x deles não aumente em dobro quando estou evitando de mover pros lados ao descer
            if(this.desceu==1){
                this.desceu=0;
            }
            if(this.descendo==true){
                this.desceu=1;
            }
            //Reseta o contador
            if (this.contadorAliens >= this.delayAliens) 
              this.contadorAliens = 0;
            }


        //Atualiza todos os objetos
        this.objetos.forEach(objeto => {
            if (!(objeto instanceof Alien)) {
                objeto.update(this.teclas, this);
            }
        });

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
