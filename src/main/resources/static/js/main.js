
var musicApi = Vue.resource('api/music')

Vue.component('music-row',{
    props: ['music'],
    template: '<div><i>( {{music.id}} )</i> {{music.title + " - "+ music.idArtist1}} </div>'


});

Vue.component('musics-list', {
    props: ['musics'],
    template:
        '<div>'+
            '<music-row v-for="music in musics" :key="music.id" :music="music"/>'+
        '</div>',
    created: function () {
        musicApi.get().then(result =>
            result.json().then(data =>
                data.forEach(music => this.musics.push(music))
            )
        )
    }
})

var app = new Vue({
    el: '#app',
    template: '<musics-list :musics="musics"/>',
    data: {
        artist: null,
        musics: []
    }
})