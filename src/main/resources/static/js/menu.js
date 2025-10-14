// Sélection de tous les parents de menus
const dropdownParents = document.querySelectorAll('.nav-bouton-parent');

dropdownParents.forEach(parent => {
    const button = parent.querySelector('.nav-bouton');
    const menu = parent.querySelector('.dropdown-menu');

    button.addEventListener('click', (e) => {
        e.preventDefault(); // empêche le lien si c'est #

        // Ferme tous les menus ouverts sauf celui-ci
        document.querySelectorAll('.dropdown-menu__open').forEach(openMenu => {
            if(openMenu !== menu) openMenu.classList.remove('dropdown-menu__open');
        });

        // Ouvre/ferme le menu du bouton cliqué
        menu.classList.toggle('dropdown-menu__open');
    });
});

// Fermer le menu si clic en dehors
document.addEventListener('click', (e) => {
    dropdownParents.forEach(parent => {
        if (!parent.contains(e.target)) {
            const menu = parent.querySelector('.dropdown-menu');
            menu.classList.remove('dropdown-menu__open');
        }
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const devisSelect = document.getElementById("devisId");

    if (!devisSelect) return;

    devisSelect.addEventListener("change", async () => {
        const devisId = devisSelect.value;
        if (!devisId || devisId === "-- Choisissez un devis --") return;

        try {
            const response = await fetch(`/creerCommande/devis/${devisId}`);
            if (!response.ok) throw new Error("Erreur chargement devis");

            const devis = await response.json();

            // Remplissage dynamique
            document.querySelector('[name="nomDocument"]').value = devis.nomDocument || "";
            document.querySelector('[name="libelle"]').value = devis.libelle || "";
            document.querySelector('[name="date"]').value = devis.date || "";
            document.querySelector('[name="poids"]').value = devis.poids || "";
            document.querySelector('[name="tva"]').value = devis.tva || "";
            document.querySelector('[name="prixKilo"]').value = devis.prixKilo || "";
            document.querySelector('[name="commentaire"]').value = devis.commentaire || "";
            document.querySelector("[name='type']").value = devis.type || "";

            // Remplir les IDs cachés
            document.getElementById("devisId").value = devis.id;
            if (devis.client) document.getElementById("clientId").value = devis.client.id;

            console.log("Devis chargé :", devis);
        } catch (error) {
            console.error("Erreur de récupération devis :", error);
            alert("Impossible de charger les informations du devis.");
        }
    });
});

