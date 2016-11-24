(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateController', CandidateController);

    CandidateController.$inject = ['$scope', '$state', 'Candidate'];

    function CandidateController ($scope, $state, Candidate) {
        var vm = this;

        vm.candidates = [];

        loadAll();

        function loadAll() {
            Candidate.query(function(result) {
                vm.candidates = result;
            });
        }
    }
})();
