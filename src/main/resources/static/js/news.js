//https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=804282cf237c4dab811d724a9a37c673


async function getNews(){
    await fetch('https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=804282cf237c4dab811d724a9a37c673')
        .then(d => d.json())
        .then(response => {
            console.log(response.results);
            for (var i = 0; i < response.results.length; i++){
                console.log(response.results[i].title);
                const output = document.getElementById('output');

                try{
                    output.innerHTML += `
                    <div class="card">
                    <div class="card-body">
                    <img src="${response.results[i]['media'][0]
                        ['media-metadata'][2].url}" alt="${response.results[i]
                        ['media'][0].caption}" title="${response.results[i]
                        ['media'][0].caption}"/>
                    <h2>${response.results[i].title}</h2>
                    <div class="card-text">
                        <p>${response.results[i].abstract}</p>
                    </div>
                    </div>
                    </div>
                    <br>
                    `
                }
                catch (err){
                    console.log(err);
                }
            }
            document.getElementById('copyright').innerHTML = response.copyright;
        })
}
getNews();

