/**
 * Created by zelentsov on 25.01.2016.
 */
notesApp.factory('NoteService', ['$http', '$q', '$timeout', 'Upload', function($http, $q, $timeout, Upload){
    var url = '/note';
    return {

        getAllNotes: function() {
            return $http.get(url)
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while fetching users');
                    return $q.reject(errResponse);
                }
            );
        },

        createNote: function(note){
            return $http.put(url, note)
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while creating user');
                    return $q.reject(errResponse);
                }
            );
        },

        updateNote: function(note, id){
            return $http.post(url + '/'+id, note)
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while updating user');
                    return $q.reject(errResponse);
                }
            );
        },

        deleteNote: function(id){
            return $http.delete(url + '/'+id)
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while deleting user');
                    return $q.reject(errResponse);
                }
            );
        },

        uploadFile: function(file, id) {
            return file.upload = Upload.upload({
                url: url + '/' + id + '/upload' ,
                resumeChunkSize: 100000,
                headers: {
                    'optional-header': 'header-value'
                },
                data: {file: file}
            })
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while deleting user');
                    return $q.reject(errResponse);
                }
            );
        }
    };

}]);