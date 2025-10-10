document.addEventListener('DOMContentLoaded', () => {
    const selectCommande = document.querySelector('#commandeId');

    if (!selectCommande) return;

    selectCommande.addEventListener('change', async () => {
        const commandeId = selectCommande.value;
        if (!commandeId) return;

        try {
            const response = await fetch(`/facture/commande/${commandeId}`);
            if (!response.ok) throw new Error('Commande introuvable');

            const commande = await response.json();

            // Remplissage dynamique
            document.querySelector('[name="nomDocument"]').value = commande.nomDocument || '';
            document.querySelector('[name="libelle"]').value = commande.libelle || '';
            document.querySelector('[name="date"]').value = commande.date || '';
            document.querySelector('[name="poids"]').value = commande.poids || '';
            document.querySelector('[name="tva"]').value = commande.tva || '';
            document.querySelector('[name="prixKilo"]').value = commande.prixKilo || '';
            document.querySelector('[name="type"]').value = commande.type || '';
            document.querySelector('[name="statutFacture"]').value = commande.statutCommande || '';
            document.querySelector('[name="commentaire"]').value = commande.commentaire || '';

            // Remplir les IDs cachés
            document.getElementById("commandeId").value = commande.id;
            if (commande.client) document.getElementById("clientId").value = commande.client.id;

        } catch (error) {
            console.error(error);
            alert('Impossible de charger les informations de la commande');
        }
    });
});
