


public class Trame {
	
	int TAILLE_TRAME_ENVOI;
	int base;//conversion des nombres en base 16
	protected char trame[];
	
	public  Trame() {
		TAILLE_TRAME_ENVOI=8;
		base=16;
		trame =new char [TAILLE_TRAME_ENVOI];
	}

	public void CalculCRC(char trame[],int taille) {
		int CR=0xFFFF,i,j,vl;
		
		
		for(i=0;i<taille;i++) {
			
			CR=CR^Character.getNumericValue(trame[i]);;
			
			for(j=0;j<8;j++) {
				vl = CR & 0x01;
				CR = CR>>1;
				if(vl==1) {
					CR = CR^0xA001;
				}
					
			}
		}
		/* Que la partie basse */
		trame[taille] = Character.forDigit(CR & 0x00FF,base);
				
		/* Que la partie haute */
		trame[taille+1] = Character.forDigit(CR>>8,base);
		

		/* Verification du bon CRC 
		printf("CRC : %d\n", CR);
		printf("CRC : %x\n", CR);
		*/
	}
	
	public void  CreationTrameEnvoi(short adresse,char trame[])
	{
		int i=0;
		
		if(trame == null)
		{
			System.out.println("Probleme malloc\n");
		}
		else
		{
			/* numero de station */
			trame[0] = 0x01;

			/* numero de la fonction */
			trame[1] = 0x03;	/* holding register */

			/* remplissage de la trame */
		
			/* Detail du Data pour Read-out of Bit Data */
			trame[3] =  Character.forDigit(adresse & 0x00FF,base); /* Partie basse de l adresse */
			trame[2] =  Character.forDigit(adresse >> 8,base); /* Partie haute de l adresse */
			
			/* Nombre d octet que l on veut lire (ici on veut lire 1 octet 0001h) */
			trame[4] = 0x00;
			trame[5] = 0x01;

			/* Calcul du CRC */
			/* TAILLE_TRAME_ENVOI est la longueur totale de la trame avec le
			 * CRC, donc ici on met -2 car il n'y a pas encore le CRC */
			CalculCRC(trame, TAILLE_TRAME_ENVOI-2);

			/* Partie affichage pour le DEBUG */
			System.out.println("Requete; ");
			
			for(i=0; i<TAILLE_TRAME_ENVOI; ++i) {
				System.out.println(trame[i]);
				System.out.println("\n");
			}
				

			
		}
	}
	
	
	
}
