$(document).ready(function () {
    //cerinta a
    $("#loading-screen-gif").show();
    $("#loading-animation").hide();
    $("#search-container").hide();
    $("#empty-field").hide();
    $("#results-panel").hide();

    //cerinta b
    $("#loading-screen-gif").delay(3000).hide(0, function () {
        $("#search-container").fadeIn();
    });

    //cerinta d
    $("#search-button").on("click", function () {
        searchGif();
        searchActions();
        moveSearchContainerToTop();
    });

    // cerinta d2
    $("#cancel-button").on("click", function () {
        hideAlert();
    });

    // cerinta d2
    $("#random-button").on("click", showRandomResultsPanel);
    $("#random-button").on("click", hideAlert);

    $("#close-button").on("click", function () {
        $("#results-panel").hide();
        resetSearchPosition();
    });
});

// cerinta d
function searchActions() {
    const searchTerm = $("#pokemon-type").val().trim().toLowerCase();

    if (searchTerm === "") {
        setTimeout(showAlert, 1000);
    } else {
        showFilteredPokemon(searchTerm);
    }
}

// cerinta d1
function showAlert() {
    $("#empty-field").show();
}

function hideAlert() {
    $("#empty-field").hide();
}

function showRandomResultsPanel() {
    $("#pokemon-list").empty();
    const randomIds = Array.from({ length: 10 }, () => Math.floor(Math.random() * 1000) + 1);

    Promise.all(
        randomIds.map(id => fetch(`https://pokeapi.co/api/v2/pokemon/${id}`).then(res => res.json()))
    )
        .then(data => {
            data.forEach((pokemonData, index) => {
                setTimeout(() => {
                    createPokemonCard(pokemonData); 
                    $("#results-panel").fadeIn();
                }, index * 100); 
            });
        })
        .catch(error => {
            $("#pokemon-list").html('<p>Eroare la încărcarea pokemonilor.</p>');
        });
}


// cerinta d2
function showFilteredPokemon(type) {
    $("#pokemon-list").empty();
    fetch(`https://pokeapi.co/api/v2/type/${type}`)
        .then(response => {
            if (!response.ok) {
                $("#pokemon-type").val('');
                alert("Tipul introdus nu este valid. Vă rugăm să încercați din nou.");
                throw new Error("Tip invalid");
            }
            return response.json();
        })
        .then(data => {
            let index = 0; 

            data.pokemon.forEach(async item => {
                const pokemonData = await fetch(`https://pokeapi.co/api/v2/pokemon/${item.pokemon.name}`).then(res => res.json());
                
                setTimeout(() => {
                    createPokemonCard(pokemonData); 
                    $("#results-panel").fadeIn();
                }, index * 100); 

                index++; 
            });
        })
        .catch(error => {
            $("#pokemon-list").html(`<p>${error.message}</p>`);
        });
}


// cerinta e
function searchGif() {
    $("#loading-animation").show();
    $("#loading-animation").delay(1000).hide(0);
}

// cerinta h
function moveSearchContainerToTop() {
    $("#search-container").addClass("top");
}

function resetSearchPosition() {
    $("#search-container").removeClass("top");
    $("#results-panel").hide();
    $("#pokemon-type").val('');
}

// cerinta i
function createPokemonCard(pokemon) {
    const abilities = pokemon.abilities.slice(0, 2).map(a => a.ability.name).join(', ');
    const cardHTML = `
        <div class="pokemon-card">
            <h3>${pokemon.name}</h3>
            <p>Tip: ${pokemon.types.map(t => t.type.name).join(', ')}</p>
            <p>ID: ${pokemon.id}</p>
            <img src="${pokemon.sprites.front_default}" alt="${pokemon.name}" class="pokemon-image">
            <button class="details-button"><i class="fas fa-info-circle"></i> Detalii</button>
            <button class="color-button"><i class="fas fa-adjust"></i> Schimbă culoarea</button>
            <div class="details-content" style="display: none;">
                <p>Height: ${pokemon.height}</p>
                <p>Weight: ${pokemon.weight}</p>
                <p>Abilities: ${abilities}</p>
            </div>
        </div>
    `;
    const $card = $(cardHTML);
    $("#pokemon-list").append($card);

    // cerinta i1
    $card.find(".details-button").on("click", function () {
        $(this).siblings(".details-content").slideToggle();
    });

    // cerinta i2
    $card.find(".color-button").on("click", function () {
        const img = $(this).siblings(".pokemon-image");
        const currentSrc = img.attr("src");
        const sprites = [
            pokemon.sprites.front_default,
            pokemon.sprites.front_shiny,
            pokemon.sprites.back_default,
            pokemon.sprites.back_shiny,
        ].filter(src => src);
        let currentIndex = sprites.indexOf(currentSrc);
        let nextIndex = (currentIndex + 1) % sprites.length;
        img.attr("src", sprites[nextIndex]);
    });
}
