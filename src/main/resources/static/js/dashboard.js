// Fonction utilitaire : charger les données JSON depuis une URL
async function fetchChartData(url) {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Erreur chargement ${url}`);
    }
    return await response.json();
}

// Graphique 1 : Chiffre d’affaires par mois
async function initChartVentesParMois() {
    try {
        const data = await fetchChartData('/dashboard/revenus');
        const ctx = document.getElementById('chartVentesParMois').getContext('2d');

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: data.labels, // ["Janvier", "Février", ...]
                datasets: [{
                    label: 'Chiffre d’affaires (€)',
                    data: data.values,
                    borderColor: 'rgba(40, 24, 128, 0.8)',
                    backgroundColor: 'rgba(40, 24, 128, 0.3)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.3
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Évolution du chiffre d’affaires mensuel'
                    },
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    } catch (err) {
        console.error('Erreur chargement du graphique Ventes :', err);
    }
}

// Graphique 2 : Top 5 Clients
async function initChartTopClients() {
    try {
        const data = await fetchChartData('/dashboard/top-clients');
        const ctx = document.getElementById('chartTopClients').getContext('2d');

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.labels, // ["Client A", "Client B", ...]
                datasets: [{
                    label: 'Chiffre d’affaires (€)',
                    data: data.values,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(255, 159, 64, 0.6)',
                        'rgba(255, 205, 86, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(153, 102, 255, 0.6)'
                    ],
                    borderColor: 'rgba(0, 0, 0, 0.1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Top 5 clients par chiffre d’affaires'
                    },
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    } catch (err) {
        console.error('Erreur chargement Top Clients :', err);
    }
}

// Initialisation des deux graphiques au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    initChartVentesParMois();
    initChartTopClients();
});
