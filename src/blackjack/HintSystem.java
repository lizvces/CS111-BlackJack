package blackjack;



public class HintSystem {

	public static String hint(int firstValue, int secValue, int dealerValue, int playerSum){
		
		//this is a basic hint system that checks the hand and gives advice based on the player's situation. 
		if((firstValue==2 && secValue==2) || (firstValue==3 && secValue==3) && (dealerValue<7 )){
			return "YOU SHOULD SPLIT";
		}else if((firstValue==2 && secValue==2) || (firstValue==3 && secValue==3) && (dealerValue>=7 )){
			return "YOU SHOULD HIT";
		}else if(firstValue==4 && secValue==4 && (dealerValue<5 || dealerValue >6)){
			return "YOU SHOULD HIT";
		}else if(firstValue==4 && secValue==4 && (dealerValue>4 && dealerValue<7)){
			return "YOU SHOULD SPLIT";
		}else if(firstValue==5 && secValue==5 && dealerValue<10){
			return "YOU SHOULD DOUBLE DOWN";
		}else if(firstValue==5 && secValue==5 && dealerValue>9){
			return "YOU SHOULD HIT";
		}else if(firstValue==6 && secValue==6 && dealerValue>6){
			return "YOU SHOULD SPLIT";
		}else if(firstValue==7 && secValue==7 && dealerValue<8){
			return "YOU SHOULD SPLIT";
		}else if(firstValue==9 && secValue==9 && (dealerValue<7 || (dealerValue>7 && dealerValue<10))){
			return "YOU SHOULD SPLIT";
		}else if(firstValue==9 && secValue==9 && (dealerValue==7 || (dealerValue>9))){
			return "YOU SHOULD STAND";
		}else if(firstValue==10 && secValue==10){
			return "YOU SHOULD STAND";
		}else if(playerSum>15){
			return "YOU SHOULD STAND";
		}else if(playerSum<15){
			return "YOU SHOULD HIT";
		}else{
			return "SORRY, YOU'RE SCREWED";
		}
		
	}
}