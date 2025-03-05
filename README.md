# Blackjack Card Counter

Hello and welcome to the **Blackjack Card Counter** app! This project is built with **Kotlin**, **Jetpack Compose**, and **Material 3** to help you practice counting cards in Blackjack. It’s great for:
- Learning basic card counting.
- Understanding your advantage as the deck’s composition changes.
- Improving your strategic thinking before sitting at a real table.

## Why This App?

1. **Interactive Learning:** Each time you see a card, you tap the appropriate button, and the app updates the **running count**, the **true count**, and your approximate **win probability**.
2. **Realistic Deck Tracking:** We track how many cards remain based on the selected deck size (1–8 decks).
3. **Instant Feedback:** The app gives a quick color-coded indication (red, yellow, green) to show who has the advantage — the dealer or the player.

## Features

- **Dynamic Deck Count:** Tap the number at the top-right corner to select from 1 to 8 decks.
- **Simple Buttons:** 
  - **-1** for high cards (10, J, Q, K, A) 
  - **0** for neutral cards (7, 8, 9) 
  - **+1** for low cards (2, 3, 4, 5, 6)
- **Accurate True Count:** The app calculates the **true count** based on how many cards are left, not just the initial deck count.
- **Live Probability Estimate:** Uses a simplified formula to estimate the player’s chance of winning each hand.
- **Animated UI:** Buttons have a slight scale and brightness animation when pressed, adding a bit of flair.

## How to Use

1. **Select Decks:** Tap the top-right number to pick the number of decks you want to practice with. The counter and card total will reset.
2. **Count Cards:** 
   - When you see a high card, tap **-1**.
   - If it's neutral, tap **0**.
   - If it’s low, tap **+1**.
   This simulates removing one card from the deck and affects the running count.
3. **Watch the Probabilities:** The **Win Probability** and **Lose Probability** at the bottom update automatically.
4. **Reset Anytime:** If you want to start fresh, tap **Reset**. This resets the count and total cards but not the deck count.
5. **Check Rules:** Tap the info icon (top-left) to see a quick reference to counting rules.

## Getting Started

1. **Clone or Download**:
   ```bash
   git clone https://github.com/YourUsername/BlackjackCardCounter.git
![InitialPhoto](https://github.com/user-attachments/assets/02dc4a2d-a0b1-4154-a7ec-b7f6369c9f0f)
![winningPhoto](https://github.com/user-attachments/assets/12d97748-3972-4a0e-bb02-da21425b17a4)
