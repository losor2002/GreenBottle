function showDetails(subscriptionId) {
    // Fetch dei dettagli
    const subscriptionDetails = document.getElementById('subscription-details');
    const subscriptionName = document.getElementById('details-name');
    const subscriptionInfo = document.getElementById('details-info');
    const subscriptionPrice = document.getElementById('details-price');

    // Mock data
    const subscriptions = [
        {id: 1, name: "Abbonamento 1", info: "Dettagli per Abbonamento 1", price: "€10/mese"},
        {id: 2, name: "Abbonamento 2", info: "Dettagli per Abbonamento 2", price: "€15/mese"},
        {id: 3, name: "Abbonamento 3", info: "Dettagli per Abbonamento 3", price: "€20/mese"}
    ];

    // Find the selected subscription
    const selectedSubscription = subscriptions.find(sub => sub.id === subscriptionId);

    // Update the details container
    if (selectedSubscription) {
        subscriptionName.textContent = selectedSubscription.name;
        subscriptionInfo.textContent = selectedSubscription.info;
        subscriptionPrice.textContent = selectedSubscription.price;
        subscriptionDetails.classList.add('active');
    } //else {
    //subscriptionDetails.classList.remove('active');
    //}
}
