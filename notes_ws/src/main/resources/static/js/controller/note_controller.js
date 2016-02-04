/**
 * Created by zelentsov on 25.01.2016.
 */
notesApp.controller('NoteController', ['$scope', 'NoteService', function($scope, NoteService) {
    var self = this;
    self.attachments = [{id:null, content:''}];
    self.changes = [];
    self.tags = [];
    self.note={id:null,title:'',content:'',tags:self.tags,attachments: self.attachments, changes:self.changes};
    self.notes=[];

    self.getAllNotes = function(){
        NoteService.getAllNotes()
            .then(
            function(d) {
                self.notes = d;
            },
            function(errResponse){
                console.error('Error while fetching Currencies');
            }
        );
    };

    self.createNote = function(note){
        NoteService.createNote(note)
            .then(
            self.getAllNotes,
            function(errResponse){
                console.error('Error while creating User.');
            }
        );
    };

    self.updateNote = function(note, id){
        NoteService.updateNote(note, id)
            .then(
            self.getAllNotes,
            function(errResponse){
                console.error('Error while updating User.');
            }
        );
    };

    self.deleteNote = function(id){
        NoteService.deleteNote(id)
            .then(
            self.getAllNotes,
            function(errResponse){
                console.error('Error while deleting User.');
            }
        );
    };

    self.getAllNotes();

    self.submit = function() {
        if(self.note.id===null){
            console.log('Saving New Note', self.note);
            self.createNote(self.note);
        }else{
            self.updateNote(self.note, self.note.id);
            console.log('Note updated with id ', self.note.id);
        }
        self.reset();
    };

    self.edit = function(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.notes.length; i++){
            if(self.notes[i].id === id) {
                self.note = angular.copy(self.notes[i]);
                break;
            }
        }
    };

    self.remove = function(id){
        console.log('id to be deleted', id);
        if(self.note.id === id) {
            self.reset();
        }
        self.deleteNote(id);
    };

    self.reset = function(){
        self.note={id:null,title:'',content:'',tags:''};
        $scope.createNote.$setPristine();
    };

    self.uploadFile = function (file) {
        if (file != null && self.note.id != null) {
            NoteService.uploadFile(file, self.note.id)
                .then(
                self.getAllNotes,
                function(errResponse){
                    console.error('Error while deleting User.');
                }
            );
        }
        self.reset();
    };
}]);