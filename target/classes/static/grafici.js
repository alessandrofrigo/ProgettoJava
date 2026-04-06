document.addEventListener('DOMContentLoaded', () => {
    fetchDataAndRenderCharts();
});

async function fetchDataAndRenderCharts() {
    try {
        const response = await fetch('/api/transazioni');
        const transazioni = await response.json();

        renderCategoryPieChart(transazioni);
        renderTimeBarChart(transazioni);

    } catch (error) {
        console.error('Errore nel recupero dei dati per i grafici:', error);
    }
}

// Genera colori casuali brillanti adatti a un tema scuro
function generateThemeColors(count) {
    const colors = [
        'rgba(59, 130, 246, 0.8)',   // Blue
        'rgba(167, 139, 250, 0.8)',  // Purple
        'rgba(244, 114, 182, 0.8)',  // Pink
        'rgba(52, 211, 153, 0.8)',   // Emerald
        'rgba(251, 191, 36, 0.8)',   // Amber
        'rgba(248, 113, 113, 0.8)',  // Red
        'rgba(96, 165, 250, 0.8)',   // Light blue
        'rgba(192, 132, 252, 0.8)'   // Light purple
    ];

    // Se ci sono più categorie dei colori predefiniti, genera varianti
    while (colors.length < count) {
        const hue = Math.floor(Math.random() * 360);
        colors.push(`hsla(${hue}, 70%, 60%, 0.8)`);
    }

    return colors.slice(0, count);
}

function renderCategoryPieChart(transazioni) {
    const categoryTotals = {};

    // Calcola il totale per ogni categoria
    transazioni.forEach(t => {
        const catName = t.categoria ? t.categoria.nome : 'Sconosciuta';
        if (!categoryTotals[catName]) {
            categoryTotals[catName] = 0;
        }
        categoryTotals[catName] += t.importo;
    });

    const categories = Object.keys(categoryTotals);
    const amounts = Object.values(categoryTotals);
    const backgroundColors = generateThemeColors(categories.length);

    const ctx = document.getElementById('categoryChart').getContext('2d');

    // Tema globale di Chart.js per adattarsi allo sfondo scuro
    Chart.defaults.color = '#cbd5e1';

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: categories,
            datasets: [{
                data: amounts,
                backgroundColor: backgroundColors,
                borderColor: 'rgba(30, 41, 59, 1)',
                borderWidth: 2,
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'right',
                    labels: {
                        color: '#f8fafc',
                        font: {
                            family: "'Inter', sans-serif"
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += new Intl.NumberFormat('it-IT', { style: 'currency', currency: 'EUR' }).format(context.parsed);
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });
}

function renderTimeBarChart(transazioni) {
    // Ordina transazioni per data crescente consideriamo solo gli ultimi 30 giorni per semplicità
    const sortedTransazioni = [...transazioni].sort((a, b) => new Date(a.data) - new Date(b.data));

    // Raggruppa per data
    const dailyTotals = {};
    sortedTransazioni.forEach(t => {
        const dateStr = new Date(t.data).toLocaleDateString('it-IT', { day: '2-digit', month: '2-digit' });
        if (!dailyTotals[dateStr]) {
            dailyTotals[dateStr] = 0;
        }
        dailyTotals[dateStr] += t.importo;
    });

    const dateLabels = Object.keys(dailyTotals);
    const amounts = Object.values(dailyTotals);

    const ctx = document.getElementById('timeChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: dateLabels,
            datasets: [{
                label: 'Spesa Giornaliera',
                data: amounts,
                backgroundColor: 'rgba(96, 165, 250, 0.8)',
                borderColor: 'rgba(59, 130, 246, 1)',
                borderWidth: 1,
                borderRadius: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    },
                    ticks: {
                        callback: function (value) {
                            return value + ' €';
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            },
            plugins: {
                legend: {
                    display: false // Nascondi la legenda per il grafico a barre singolo
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            return new Intl.NumberFormat('it-IT', { style: 'currency', currency: 'EUR' }).format(context.raw);
                        }
                    }
                }
            }
        }
    });
}
